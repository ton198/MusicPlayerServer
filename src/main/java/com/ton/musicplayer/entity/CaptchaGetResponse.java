package com.ton.musicplayer.entity;

public class CaptchaGetResponse extends BasicErrorResponse {

    private String url;
    private String captcha_id;

    public CaptchaGetResponse(String err) {
        this(err, null, null);
    }

    public CaptchaGetResponse() {}


    public CaptchaGetResponse(String err, String captchaId, String url) {
        super(err);
        this.captcha_id = captchaId;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaptchaId() {
        return captcha_id;
    }

    public void setCaptchaId(String captchaId) {
        this.captcha_id = captchaId;
    }
}
