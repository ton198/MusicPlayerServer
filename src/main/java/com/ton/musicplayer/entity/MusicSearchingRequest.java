package com.ton.musicplayer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.codehaus.jettison.json.JSONString;

import java.lang.reflect.Field;

public class MusicSearchingRequest extends BasicRequest{

    @Nullable
    @JsonProperty(value = "keywords")
    private String keywords;
    @JsonProperty(value = "location")
    private String location;
    @JsonProperty(value = "page")
    private int page;
    @JsonProperty(value = "search_id")
    private String search_id;

    public MusicSearchingRequest() {}

    public MusicSearchingRequest(String keywords, String location, int page, String searchId) {
        this.keywords = keywords;
        this.location = location;
        this.page = page;
        this.search_id = searchId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSearchId(String searchId) {
        this.search_id = searchId;
    }

    public String getSearchId() {
        return search_id;
    }


    public MusicSearchingRequest copy() {
        return new MusicSearchingRequest(this.keywords, this.location, this.page, this.search_id);
    }

    @Override
    public boolean validRequest() {
        for (Field f : this.getClass().getDeclaredFields()) {
            Object o = null;
            try {
                o = f.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (o == null || "".equals(o)) {
                return false;
            }
        }
        return true;
    }
}
