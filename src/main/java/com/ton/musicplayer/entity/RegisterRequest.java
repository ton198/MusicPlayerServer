package com.ton.musicplayer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest extends BasicRequest {

    @JsonProperty(value = "username")
    String username;
    @JsonProperty(value = "password")
    String password;
    @JsonProperty(value = "email")
    String email;
    @JsonProperty(value = "phone_number")
    String phone_number;
    @JsonProperty(value = "captcha_id")
    String captcha_id;
    @JsonProperty(value = "captcha_text")
    String captcha_text;

    public RegisterRequest() {}

    public RegisterRequest(String username, String password, String email, String phoneNumber, String captchaId, String captchaText) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone_number = phoneNumber;
        this.captcha_id = captchaId;
        this.captcha_text = captchaText;
    }

    @Override
    public boolean validRequest() {
        return (username != null || email != null || phone_number != null) && password != null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone_number = phoneNumber;
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
}
