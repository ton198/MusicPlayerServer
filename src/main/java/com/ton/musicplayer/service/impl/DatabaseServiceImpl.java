package com.ton.musicplayer.service.impl;

import com.ton.musicplayer.dao.DatabaseMapper;
import com.ton.musicplayer.entity.MusicInfo;
import com.ton.musicplayer.entity.MusicPublicFields;
import com.ton.musicplayer.entity.RelationInfo;
import com.ton.musicplayer.entity.UserInfo;
import com.ton.musicplayer.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    DatabaseMapper databaseMapper;

    @Autowired
    public DatabaseServiceImpl(DatabaseMapper databaseMapper) {
        this.databaseMapper = databaseMapper;
    }

    @Override
    public boolean checkPassword(String userId, String password) {
        return databaseMapper.checkPassword(userId, password) == 1;
    }

    @Override
    public boolean checkMusicExistsByMusicLyricMd5Code(String musicMd5Code, String lyricMd5Code) {
        return databaseMapper.checkMusicExistsByMusicLyricMd5Code(musicMd5Code, lyricMd5Code) == 1;
    }

    @Override
    public String getMusicUrlByMusicMd5Code(String musicMd5Code) {
        return databaseMapper.getMusicUrlByMusicMd5Code(musicMd5Code);
    }



    @Override
    public void addMusic(MusicInfo musicInfo) {
        databaseMapper.addMusic(musicInfo);
    }

    @Override
    public void addRelation(RelationInfo relationInfo) {
        databaseMapper.addRelation(relationInfo);
    }

    @Override
    public UserInfo getUserByUserId(String userId) {
        return databaseMapper.getUserByUserId(userId);
    }

    @Override
    public MusicInfo getMusicById(String musicId) {
        return databaseMapper.getMusicByMusicId(musicId);
    }

    @Override
    public MusicPublicFields[] getFavoriteMusicsByUserId(String userId, int start, int limit) {
        return databaseMapper.getFavoriteMusicsByUserId(userId, start, limit);
    }

    @Override
    public void addUser(UserInfo userInfo) {
        databaseMapper.addUser(userInfo);
    }

    @Override
    public String getLyricByMusicId(String musicId) {
        return databaseMapper.getLyricByMusicId(musicId);
    }

    @Override
    public boolean checkRelationExists(String userId, String musicId) {
        return databaseMapper.checkRelationExists(userId, musicId) == 1;
    }

    @Override
    public void deleteRelation(String userId, String musicId) {
        databaseMapper.deleteRelation(userId, musicId);
    }

    @Override
    public boolean checkMusicExistsByMusicName(String name) {
        return databaseMapper.checkMusicExistsByMusicName(name) == 1;
    }

    @Override
    public MusicPublicFields[] getPublicMusics(int start, int limit) {
        return databaseMapper.getPublicMusics(start, limit);
    }

    @Override
    public MusicPublicFields[] searchPublicMusics(String keywords, int start, int limit) {
        return databaseMapper.searchPublicMusics(keywords, start, limit);
    }

    @Override
    public MusicPublicFields[] searchOwnMusics(String keywords, int start, int limit, String userId) {
        return databaseMapper.searchOwnMusics(keywords, start, limit, userId);
    }

    @Override
    public String getUserIdByUsername(String username) {
        return databaseMapper.getUserIdByUsername(username);
    }

    @Override
    public String getUserIdByEmail(String email) {
        return databaseMapper.getUserIdByEmail(email);
    }

    @Override
    public String getUserIdByPhoneNumber(String phoneNumber) {
        return databaseMapper.getUserIdByPhoneNumber(phoneNumber);
    }

}
