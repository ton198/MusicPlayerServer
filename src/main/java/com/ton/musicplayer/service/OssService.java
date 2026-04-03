package com.ton.musicplayer.service;


import java.io.InputStream;

public interface OssService {

    void uploadFile(String path, byte[] data);

    String getTemperateUrl(String path);
}
