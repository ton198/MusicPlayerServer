package com.ton.musicplayer.entity;

public class RelationInfo {

    private String record_id;
    private String user_id;
    private String music_id;

    public RelationInfo() {
    }

    public RelationInfo(String record_id, String user_id, String music_id) {
        this.record_id = record_id;
        this.user_id = user_id;
        this.music_id = music_id;
    }

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
