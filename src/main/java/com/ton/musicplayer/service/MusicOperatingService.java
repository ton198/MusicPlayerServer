package com.ton.musicplayer.service;

import com.ton.musicplayer.entity.BasicErrorResponse;
import com.ton.musicplayer.entity.FavoritesOperatingPrivateRequest;

public interface MusicOperatingService {

    BasicErrorResponse addToFavorites(FavoritesOperatingPrivateRequest request);

    BasicErrorResponse removeFromFavorites(FavoritesOperatingPrivateRequest request);

    BasicErrorResponse checkMusicExistsInFavorites(FavoritesOperatingPrivateRequest request);

}
