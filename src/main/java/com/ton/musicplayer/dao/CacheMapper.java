package com.ton.musicplayer.dao;

import com.ton.musicplayer.entity.MusicPublicFields;
import com.ton.musicplayer.entity.MusicSearchingRequest;

public interface CacheMapper {

    MusicPublicFields[] getSearchingCache(MusicSearchingRequest request);

    void setSearchingCache(MusicSearchingRequest metadata, MusicPublicFields[] data);

    void setCaptchaCache(String captchaId, String captchaText);

    String getCaptchaCache(String captchaId);

    void removeCaptchaCache(String captchaId);

}
