package com.ton.musicplayer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DownloadRequest extends BasicRequest{

    @JsonProperty("music_id")
    private String music_id;

    public String getMusicId() {
        return music_id;
    }

    public void setMusicId(String musicId) {
        this.music_id = musicId;
    }

    public DownloadRequest(String musicId) {
        this.music_id = musicId;
    }

    public DownloadRequest() {}

    @Override
    public boolean validRequest() {
        return music_id != null;
    }
}
