package com.ton.musicplayer.service;

import com.ton.musicplayer.entity.*;

public interface DatabaseService {

    UserInfo getUserByUserId(String userId);

    MusicInfo getMusicById(String musicId);

    MusicPublicFields[] getFavoriteMusicsByUserId(String userId, int start, int limit);

    MusicPublicFields[] getPublicMusics(int start, int limit);

    MusicPublicFields[] searchPublicMusics(String keywords, int start, int limit);

    MusicPublicFields[] searchOwnMusics(String keywords, int start ,int limit, String userId);

    String getUserIdByUsername(String username);

    String getUserIdByEmail(String email);

    String getUserIdByPhoneNumber(String phoneNumber);

    boolean checkPassword(String userId, String password);

    boolean checkMusicExistsByMusicLyricMd5Code(String musicMd5Code, String lyricMd5Code);

    String getMusicUrlByMusicMd5Code(String musicMd5Code);

    void addMusic(MusicInfo musicInfo);

    void addUser(UserInfo userInfo);

    void addRelation(RelationInfo relationInfo);

    String getLyricByMusicId(String musicId);

    boolean checkRelationExists(String userId, String musicId);

    void deleteRelation(String userId, String musicId);

    boolean checkMusicExistsByMusicName(String name);

}
