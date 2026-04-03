package com.ton.musicplayer.entity;

public class MusicSearchingResponse extends BasicErrorResponse {

    private MusicPublicFields[] result;

    public MusicSearchingResponse() {}

    public MusicSearchingResponse(String err) {
        super(err);
        this.result = null;
    }

    public MusicSearchingResponse(String err, MusicPublicFields[] result) {
        super(err);
        this.result = result;
    }

    public MusicPublicFields[] getResult() {
        return result;
    }

    public void setResult(MusicPublicFields[] result) {
        this.result = result;
    }
}
