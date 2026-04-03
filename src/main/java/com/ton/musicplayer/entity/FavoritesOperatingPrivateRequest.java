package com.ton.musicplayer.entity;

public class FavoritesOperatingPrivateRequest extends BasicRequest{

    private String music_id;

    private String user_id;

    public String getMusicId() {
        return music_id;
    }

    public void setMusicId(String musicId) {
        this.music_id = musicId;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String userId) {
        this.user_id = userId;
    }

    public FavoritesOperatingPrivateRequest() {
    }

    public FavoritesOperatingPrivateRequest(String userId, String musicId) {
        this.user_id = userId;
        this.music_id = musicId;
    }

    @Override
    public boolean validRequest() {
        return music_id != null && user_id != null;
    }
}
