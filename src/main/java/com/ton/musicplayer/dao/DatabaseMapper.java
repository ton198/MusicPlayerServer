package com.ton.musicplayer.dao;

import com.ton.musicplayer.entity.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DatabaseMapper {

    UserInfo getUserByUserId(String userId);

    MusicInfo getMusicByMusicId(String musicId);

    MusicPublicFields[] getFavoriteMusicsByUserId(String userId, int start, int limit);

    MusicPublicFields[] getPublicMusics(int start, int limit);

    String getMusicUrlByMusicMd5Code(String musicMd5Code);

    /**
     *
     * @param musicMd5Code
     * @param lyricMd5Code
     * @return type: int
     * 0 for false, 1 for true.
     */
    int checkMusicExistsByMusicLyricMd5Code(String musicMd5Code, String lyricMd5Code);

    int checkMusicExistsByMusicName(String name);

    void addMusic(MusicInfo musicInfo);

    int checkPassword(String userId, String password);

    String getUserIdByUsername(String username);

    String getUserIdByEmail(String email);

    String getUserIdByPhoneNumber(String phoneNumber);

    void addUser(UserInfo userInfo);

    void addRelation(RelationInfo relationInfo);

    void deleteRelation(String userId, String musicId);

    MusicPublicFields[] searchPublicMusics(String keywords, int start, int limit);

    MusicPublicFields[] searchOwnMusics(String keywords, int start ,int limit, String userId);

    String getLyricByMusicId(String musicId);

    int checkRelationExists(String userId, String musicId);

}
