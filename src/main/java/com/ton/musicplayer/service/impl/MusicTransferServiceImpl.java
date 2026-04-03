package com.ton.musicplayer.service.impl;

import com.ton.musicplayer.entity.*;
import com.ton.musicplayer.service.DatabaseService;
import com.ton.musicplayer.service.MusicTransferService;
import com.ton.musicplayer.service.OssService;
import com.ton.musicplayer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Service
public class MusicTransferServiceImpl implements MusicTransferService {

    DatabaseService databaseService;
    OssService ossService;
    UserService userService;
    Logger logger;

    @Autowired
    public MusicTransferServiceImpl(DatabaseService databaseService, OssService ossService, UserService userService) {
        this.databaseService = databaseService;
        this.ossService = ossService;
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public BasicErrorResponse UploadMusic(UploadRequest uploadRequest, String owner) {

        String musicMd5Code;
        byte[] musicData;
        try {
            musicData = uploadRequest.getFile().getInputStream().readAllBytes();
            musicMd5Code = DigestUtils.md5DigestAsHex(musicData);
            logger.info("md5: " + musicMd5Code);
        } catch (IOException e) {
            logger.error(e.toString());
            return new BasicErrorResponse("UNKNOWN_ERROR");
        }
        MusicInfo musicInfo = new MusicInfo();
        musicInfo.setName(uploadRequest.getName());
        musicInfo.setOwner(owner);
        musicInfo.setAccessibility(uploadRequest.getAccessibility());
        musicInfo.setLyric(uploadRequest.getLyric());
        musicInfo.setComment(uploadRequest.getComment());
        musicInfo.setMark(uploadRequest.getMark());

        String lyricMd5Code = DigestUtils.md5DigestAsHex(musicInfo.getLyric().getBytes(StandardCharsets.UTF_8));

        if (databaseService.checkMusicExistsByMusicLyricMd5Code(musicMd5Code, lyricMd5Code) || databaseService.checkMusicExistsByMusicName(uploadRequest.getName())) {
            return new BasicErrorResponse("ALREADY_EXIST");
        }

        musicInfo.setMusicId(UUID.randomUUID().toString());
        musicInfo.setLyricMd5Code(lyricMd5Code);
        musicInfo.setMusicMd5Code(musicMd5Code);
        musicInfo.setUrl(Objects.requireNonNullElseGet(databaseService.getMusicUrlByMusicMd5Code(musicMd5Code), () -> UUID.randomUUID().toString())); // if url equals null return the generated url

        databaseService.addMusic(musicInfo);

        RelationInfo relationInfo = new RelationInfo();
        relationInfo.setRecordId(UUID.randomUUID().toString());
        relationInfo.setUserId(owner);
        relationInfo.setMusicId(musicInfo.getMusicId());
        databaseService.addRelation(relationInfo);

        ossService.uploadFile(musicInfo.getUrl(), musicData);

        return new BasicErrorResponse("NONE");
    }

    @Override
    public MusicDownloadResponse DownloadMusic(String musicId, String userId) {

        MusicInfo musicInfo = databaseService.getMusicById(musicId);

        //check permission and user login.
        if (userId == null) { // cannot get userId from session, means the session does not exist
            return new MusicDownloadResponse("LOGIN_EXPIRED");
        }
        if (musicInfo == null) {
            return new MusicDownloadResponse("NO_SUCH_MUSIC");
        }
        if ("PRIVATE".equals(musicInfo.getAccessibility()) && !userId.equals(musicInfo.getOwner())) {
            return new MusicDownloadResponse("NO_PERMISSION");
        }

        String tempUrl = ossService.getTemperateUrl(musicInfo.getUrl());
        if (tempUrl == null) {
            return new MusicDownloadResponse("UNKNOWN_ERROR");
        }

        String lyric = databaseService.getLyricByMusicId(musicId);

        return new MusicDownloadResponse("NONE", tempUrl, lyric);
    }
}
