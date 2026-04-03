package com.ton.musicplayer.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {

    @Value("${searching.page-length}")
    private int pageLength;
    @Value("${searching.cache-number}")
    private int cacheNumber;
    @Value("${cache.expiration-minute}")
    private int cacheExpiredTimeInMinute;
    @Value("${oss.endpoint}")
    private String ossEndpoint;
    @Value("${oss.bucket-name}")
    private String ossBucketName;
    @Value("${oss.access-key-id:000}")
    private String ossAccessKeyId;
    @Value("${oss.access-key-secret}")
    private String ossAccessKeySecret;

    public int getPageLength() {
        return pageLength;
    }

    public int getCacheNumber() {
        return cacheNumber;
    }

    public int getCacheExpiredTimeInMinute() {
        return cacheExpiredTimeInMinute;
    }

    public String getOssEndpoint() {
        return ossEndpoint;
    }

    public String getOssBucketName() {
        return ossBucketName;
    }

    public String getOssAccessKeyId() {
        return ossAccessKeyId;
    }

    public String getOssAccessKeySecret() {
        return ossAccessKeySecret;
    }
}
