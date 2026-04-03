package com.ton.musicplayer.interceptor;

import com.ton.musicplayer.entity.CaptchaVerifyRequest;
import com.ton.musicplayer.service.CaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CaptchaInterceptor implements HandlerInterceptor {


    CaptchaService captchaService;

    @Autowired
    public CaptchaInterceptor(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {
        CaptchaVerifyRequest captchaVerifyRequest = new CaptchaVerifyRequest((String)httpServletRequest.getAttribute("captcha_id"), (String)httpServletRequest.getAttribute("captcha_text"));
        return captchaService.verify(captchaVerifyRequest.getCaptchaId(), captchaVerifyRequest.getCaptchaText());
    }
}
