package com.ton.musicplayer.service;

import com.ton.musicplayer.entity.CaptchaGetRequest;

public interface CaptchaService {

    byte[] getCaptcha(CaptchaGetRequest request);

    boolean verify(String captchaId, String captchaText);

}
