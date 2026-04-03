package com.ton.musicplayer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MusicPublicFields {

    @JsonProperty("music_id")
    private String music_id;
    private String name;
    private String owner;
    private String comment;
    private String mark;


    public MusicPublicFields() {}

    public MusicPublicFields(String music_id, String name, String owner, String comment, String mark) {
        this.music_id = music_id;
        this.name = name;
        this.owner = owner;
        this.comment = comment;
        this.mark = mark;
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
