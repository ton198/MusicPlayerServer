package com.ton.musicplayer.dao.impl;

import com.ton.musicplayer.dao.CacheMapper;
import com.ton.musicplayer.config.MyRedisTemplate;
import com.ton.musicplayer.entity.MusicPublicFields;
import com.ton.musicplayer.entity.MusicSearchingRequest;
import com.ton.musicplayer.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;


@Repository
public class CacheMapperImpl implements CacheMapper {


    private final ValueOperations<MusicSearchingRequest, MusicPublicFields[]> myRedisOp;
    private final ValueOperations<String, String> stringRedisOp;
    private final StringRedisTemplate stringRedisTemplate;
    private final Constant constant;


    @Autowired
    public CacheMapperImpl(MyRedisTemplate myRedisTemplate, StringRedisTemplate stringRedisTemplate, Constant constant) {
        this.myRedisOp = myRedisTemplate.opsForValue();
        this.stringRedisTemplate = stringRedisTemplate;
        this.stringRedisOp = stringRedisTemplate.opsForValue();
        this.constant = constant;
    }

    @Override
    public MusicPublicFields[] getSearchingCache(MusicSearchingRequest request) {
        return myRedisOp.getAndExpire(request, constant.getCacheExpiredTimeInMinute(), TimeUnit.MINUTES);
    }

    @Override
    public void setSearchingCache(MusicSearchingRequest metadata, MusicPublicFields[] data) {
        myRedisOp.setIfAbsent(metadata, data, constant.getCacheExpiredTimeInMinute(), TimeUnit.MINUTES);
    }

    @Override
    public void setCaptchaCache(String captchaId, String captchaText) {
        stringRedisOp.set(captchaId, captchaText, constant.getCacheExpiredTimeInMinute(), TimeUnit.MINUTES);
    }

    public String getCaptchaCache(String captchaId) {
        return stringRedisOp.get(captchaId);
    }

    @Override
    public void removeCaptchaCache(String captchaId) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(captchaId))) { // null pointer exception?
            stringRedisTemplate.delete(captchaId);
        }
    }
}
