package com.ton.musicplayer.service;

import com.ton.musicplayer.entity.MusicSearchingRequest;
import com.ton.musicplayer.entity.MusicSearchingResponse;

public interface MusicSearchingService {

    MusicSearchingResponse searchMusics(MusicSearchingRequest request, String userId);

}
