package com.ton.musicplayer.service.impl;

import com.ton.musicplayer.entity.*;
import com.ton.musicplayer.service.DatabaseService;
import com.ton.musicplayer.service.MusicOperatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MusicOperatingServiceImpl implements MusicOperatingService {

    DatabaseService databaseService;
    Logger logger;

    @Autowired
    public MusicOperatingServiceImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public BasicErrorResponse addToFavorites(FavoritesOperatingPrivateRequest request) {
        if (!request.validRequest()) return new BasicErrorResponse("INVALID_REQUEST");

        logger.info(request.toString());

        UserInfo userInfo = databaseService.getUserByUserId(request.getUserId());
        if (userInfo == null) return new BasicErrorResponse("NO_SUCH_USER");
        MusicInfo musicInfo = databaseService.getMusicById(request.getMusicId());
        if (musicInfo == null) return new BasicErrorResponse("NO_SUCH_MUSIC");

        if (databaseService.checkRelationExists(request.getUserId(), request.getMusicId()))
            return new BasicErrorResponse("ALREADY_EXIST");
        if ("PRIVATE".equals(musicInfo.getAccessibility()))
            return new BasicErrorResponse("NO_PERMISSION");

        databaseService.addRelation(new RelationInfo(UUID.randomUUID().toString(), request.getUserId(), request.getMusicId()));
        return new BasicErrorResponse("NONE");
    }

    @Override
    public BasicErrorResponse removeFromFavorites(FavoritesOperatingPrivateRequest request) {
        if (!databaseService.checkRelationExists(request.getUserId(), request.getMusicId()))
            return new BasicErrorResponse("NO_SUCH_RELATION");

        UserInfo userInfo = databaseService.getUserByUserId(request.getUserId());
        if (userInfo == null) return new BasicErrorResponse("INVALID_REQUEST");
        MusicInfo musicInfo = databaseService.getMusicById(request.getMusicId());
        if (musicInfo == null) return new BasicErrorResponse("INVALID_REQUEST");

        if (userInfo.getUserId().equals(musicInfo.getOwner())) return new BasicErrorResponse("CANNOT_REMOVE_OWNED_MUSIC");

        databaseService.deleteRelation(request.getUserId(), request.getMusicId());
        return new BasicErrorResponse("NONE");
    }

    @Override
    public BasicErrorResponse checkMusicExistsInFavorites(FavoritesOperatingPrivateRequest request) {
        if (databaseService.checkRelationExists(request.getUserId(), request.getMusicId())) {
            return new BasicErrorResponse("IN_FAVORITES");
        } else {
            return new BasicErrorResponse("NOT_IN_FAVORITES");
        }
    }
}
