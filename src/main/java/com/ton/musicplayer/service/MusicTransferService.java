package com.ton.musicplayer.service;

import com.ton.musicplayer.entity.BasicErrorResponse;
import com.ton.musicplayer.entity.MusicDownloadResponse;
import com.ton.musicplayer.entity.MusicInfo;
import com.ton.musicplayer.entity.UploadRequest;

import java.io.IOException;
import java.io.InputStream;

public interface MusicTransferService {

    BasicErrorResponse UploadMusic(UploadRequest uploadRequest, String owner);

    MusicDownloadResponse DownloadMusic(String musicId, String userId);
}
