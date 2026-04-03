package com.ton.musicplayer.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BasicErrorResponse {

    private String err;

    public BasicErrorResponse() {}

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    @JsonCreator
    public BasicErrorResponse(@JsonProperty("err") String err) {
        this.err = err;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
