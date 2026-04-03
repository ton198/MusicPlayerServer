package com.ton.musicplayer.entity;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class UploadRequest extends BasicRequest {

    private String name;
    private String lyric;
    private String accessibility;
    private MultipartFile file;
    private String comment;
    private String mark;

    public UploadRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
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

    @Override
    public String toString() {
        return "name" + this.name + ";lyric" + this.lyric + ";accessibility" + accessibility;
    }

    @Override
    public boolean validRequest() {
        return name != null && lyric != null && accessibility != null && file != null;
    }
}
