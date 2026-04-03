package com.ton.musicplayer.service.impl;

import com.ton.musicplayer.dao.CacheMapper;
import com.ton.musicplayer.entity.MusicInfo;
import com.ton.musicplayer.entity.MusicPublicFields;
import com.ton.musicplayer.entity.MusicSearchingRequest;
import com.ton.musicplayer.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CacheServiceImpl implements CacheService {

    CacheMapper cacheMapper;

    @Autowired
    public CacheServiceImpl(CacheMapper cacheMapper) {
        this.cacheMapper = cacheMapper;
    }

    @Override
    public MusicPublicFields[] getSearchingCache(MusicSearchingRequest request) {
        return cacheMapper.getSearchingCache(request);
    }

    @Override
    public void setSearchingCache(MusicSearchingRequest request, MusicPublicFields[] data) {
        cacheMapper.setSearchingCache(request, data);
    }

    @Override
    public void setCaptchaCache(String captchaId, String captchaText) {
        cacheMapper.setCaptchaCache(captchaId, captchaText);
    }

    @Override
    public void removeCaptchaCache(String captchaId) {
        cacheMapper.removeCaptchaCache(captchaId);
    }

    @Override
    public String getCaptchaCache(String captchaId) {
        return cacheMapper.getCaptchaCache(captchaId);
    }

}
