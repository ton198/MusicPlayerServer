package com.ton.musicplayer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest extends BasicRequest {

    @JsonProperty(value = "identifier")
    private String identifier;
    @JsonProperty(value = "password")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean validRequest() {
        return false;
    }
}
