package com.ton.musicplayer.service.impl;

import com.ton.musicplayer.config.MyCaptcha;
import com.ton.musicplayer.entity.CaptchaGetRequest;
import com.ton.musicplayer.service.CacheService;
import com.ton.musicplayer.service.CaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    MyCaptcha myCaptcha;
    CacheService cacheService;
    Logger logger;

    @Autowired
    public CaptchaServiceImpl(MyCaptcha myCaptcha, CacheService cacheService) {
        this.myCaptcha = myCaptcha;
        this.cacheService = cacheService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public byte[] getCaptcha(CaptchaGetRequest request) {

        if (!request.validRequest()) {
            logger.info("request_id:" + request.getRequestId());
            logger.info("invalid captcha request");
            return null;
        }

        String captchaText = myCaptcha.createText();

        cacheService.setCaptchaCache(request.getRequestId(), captchaText);

        BufferedImage image = myCaptcha.createImage(captchaText);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            logger.error(e.toString());
            return null;
        }
        return outputStream.toByteArray();
    }

    @Override
    public boolean verify(String captchaId, String captchaText) {
        logger.info("id: " + captchaId +  "captcha: " + cacheService.getCaptchaCache(captchaId));
        if (captchaId != null && captchaText != null && captchaText.equals(cacheService.getCaptchaCache(captchaId))) {
            cacheService.removeCaptchaCache(captchaId);
            return true;
        }
        return false;
    }
}
