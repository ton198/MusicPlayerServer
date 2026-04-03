package com.ton.musicplayer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CaptchaGetRequest extends BasicRequest {

    private String request_id;

    public CaptchaGetRequest() {}

    public CaptchaGetRequest(String requestId) {
        this.request_id = requestId;
    }

    public String getRequestId() {
        return request_id;
    }

    public void setRequestId(String requestId) {
        this.request_id = requestId;
    }

    @Override
    public boolean validRequest() {
        return request_id != null && !request_id.isEmpty();
    }
}
