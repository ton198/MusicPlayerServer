package com.ton.musicplayer.entity;

public class MusicInfo {

    private String music_id;
    private String name;
    private String owner;
    private String accessibility;
    private String url; // this url is not shared url. This url is used for locating the music file.
    private String music_md5_code;
    private String lyric;
    private String lyric_md5_code;
    private String comment;
    private String mark;

    public MusicInfo() {}

    public String getLyricMd5Code() {
        return lyric_md5_code;
    }

    public void setLyricMd5Code(String lyricMd5Code) {
        this.lyric_md5_code = lyricMd5Code;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getMusicId() {
        return music_id;
    }

    public void setMusicId(String musicId) {
        this.music_id = musicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMusicMd5Code() {
        return music_md5_code;
    }

    public void setMusicMd5Code(String musicMd5Code) {
        this.music_md5_code = musicMd5Code;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
