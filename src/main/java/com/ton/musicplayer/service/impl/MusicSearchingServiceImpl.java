package com.ton.musicplayer.service.impl;

import com.ton.musicplayer.entity.*;
import com.ton.musicplayer.service.DatabaseService;
import com.ton.musicplayer.service.MusicSearchingService;
import com.ton.musicplayer.service.CacheService;
import com.ton.musicplayer.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MusicSearchingServiceImpl implements MusicSearchingService {

    DatabaseService databaseService;
    CacheService cacheService;
    Constant constant;
    Logger logger;

    @Autowired
    public MusicSearchingServiceImpl(DatabaseService databaseService, CacheService cacheService, Constant constant) {
        this.databaseService = databaseService;
        this.cacheService = cacheService;
        this.constant = constant;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public MusicSearchingResponse searchMusics(MusicSearchingRequest request, String userId) {

        MusicPublicFields[] result = cacheService.getSearchingCache(request);
        if (result == null) {
            // search in mysql and put result into redis
            MusicPublicFields[] cachedResult;
            if ("PUBLIC".equals(request.getLocation())) {
                if ("".equals(request.getKeywords())) {
                    cachedResult = databaseService.getPublicMusics(request.getPage() * constant.getPageLength(),
                            constant.getPageLength() * constant.getCacheNumber());
                } else {
                    cachedResult = databaseService.searchPublicMusics(request.getKeywords(),
                            request.getPage() * constant.getPageLength(),
                            constant.getPageLength() * constant.getCacheNumber());
                }
            } else if ("PRIVATE".equals(request.getLocation())) {
                if ("".equals(request.getKeywords())) {
                    cachedResult = databaseService.getFavoriteMusicsByUserId(userId,
                            request.getPage() * constant.getPageLength(),
                            constant.getPageLength() * constant.getCacheNumber());
                } else {
                    cachedResult = databaseService.searchOwnMusics(request.getKeywords(),
                            request.getPage() * constant.getPageLength(),
                            constant.getPageLength() * constant.getCacheNumber(),
                            userId);
                }
            } else {
                return new MusicSearchingResponse("INVALID_REQUEST");
            }
            result = copyList(cachedResult, 0, constant.getPageLength());

            int actualPageNumber = cachedResult.length / constant.getPageLength();
            for (int i = 0;i < actualPageNumber; i++) {
                MusicSearchingRequest cachedRequest = request.copy();
                cachedRequest.setPage(cachedRequest.getPage() + i);
                cacheService.setSearchingCache(
                        cachedRequest,
                        copyList(cachedResult, i * constant.getPageLength(), (i + 1) * constant.getPageLength()));
            }
        }

        return new MusicSearchingResponse("NONE", result);
    }

    private MusicPublicFields[] copyList(MusicPublicFields[] origin, int from, int to) {
        if (origin.length < to) {
            return Arrays.copyOfRange(origin, from, origin.length);
        }
        return Arrays.copyOfRange(origin, from, to);
    }
}
