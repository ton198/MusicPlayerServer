package com.ton.musicplayer.entity;

public class UserMusicRelationInfo {
    
    private String record_id;
    private String user_id;
    private String music_id;

    public UserMusicRelationInfo() {}

    public String getRecordId() {
        return record_id;
    }

    public void setRecordId(String recordId) {
        this.record_id = recordId;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String userId) {
        this.user_id = userId;
    }

    public String getMusicId() {
        return music_id;
    }

    public void setMusicId(String musicId) {
        this.music_id = musicId;
    }
}
