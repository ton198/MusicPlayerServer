package com.ton.musicplayer.service;

import com.ton.musicplayer.entity.MusicPublicFields;
import com.ton.musicplayer.entity.MusicSearchingRequest;

public interface CacheService {

    MusicPublicFields[] getSearchingCache(MusicSearchingRequest request);

    void setSearchingCache(MusicSearchingRequest request, MusicPublicFields[] data);

    String getCaptchaCache(String captchaId);

    void setCaptchaCache(String captchaId, String captchaText);

    void removeCaptchaCache(String captchaId);

}
