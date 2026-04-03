package com.ton.musicplayer.entity;

public class MusicDownloadResponse extends BasicErrorResponse {

    private String url;
    private String lyric;

    public MusicDownloadResponse() {}

    public MusicDownloadResponse(String err, String url, String lyric) {
        super(err);
        this.url = url;
        this.lyric = lyric;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public MusicDownloadResponse(String err) {
        this(err, null, null);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
