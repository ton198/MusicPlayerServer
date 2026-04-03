package com.ton.musicplayer.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.model.*;
import com.ton.musicplayer.service.OssService;
import com.ton.musicplayer.util.Constant;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;


@Service
public class OssServiceImpl implements OssService {

    OSS ossClient;
    Logger logger;
    Constant constant;

    @Autowired
    public OssServiceImpl(Constant constant) {
        this.constant = constant;

        logger = LoggerFactory.getLogger(this.getClass());
        ossClient = new OSSClientBuilder().build(constant.getOssEndpoint(), CredentialsProviderFactory.newDefaultCredentialProvider(constant.getOssAccessKeyId(), constant.getOssAccessKeySecret()));
    }

    @Override
    public void uploadFile(String path, byte[] data) {
        PutObjectRequest request = new PutObjectRequest(constant.getOssBucketName(), path, new ByteArrayInputStream(data));
        ossClient.putObject(request);
    }

    @Override
    public String getTemperateUrl(String path) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(constant.getOssBucketName(), path, HttpMethod.GET);
        request.setExpiration(new Date(System.currentTimeMillis() + 300000)); //5 min
        String url = null;
        try {
            url = ossClient.generatePresignedUrl(request).toString();
        } catch (ClientException e) {
            logger.error(e.toString());
        }
        return url;
    }
}
