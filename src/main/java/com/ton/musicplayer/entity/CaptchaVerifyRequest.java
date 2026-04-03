package com.ton.musicplayer.entity;

public class CaptchaVerifyRequest extends BasicRequest{
    private String captcha_id;
    private String captcha_text;

    public CaptchaVerifyRequest() {}

    public CaptchaVerifyRequest(String captchaId, String captchaText) {
        this.captcha_id = captchaId;
        this.captcha_text = captchaText;
    }


    public String getCaptchaId() {
        return captcha_id;
    }

    public void setCaptchaId(String captchaId) {
        this.captcha_id = captchaId;
    }

    public String getCaptchaText() {
        return captcha_text;
    }

    public void setCaptchaText(String captchaText) {
        this.captcha_text = captchaText;
    }

    @Override
    public boolean validRequest() {
        return captcha_id != null && !captcha_id.isEmpty() && captcha_text != null && captcha_text.isEmpty();
    }
}
