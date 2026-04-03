package com.ton.musicplayer.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    Logger logger;
    public SessionInterceptor() {
        logger = LoggerFactory.getLogger(this.getClass());
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (request.getSession(false) == null) {

            response.sendError(401);
            logger.info("no session");
            return false; // ignore request
        }
        return true; // continue handling
    }
}
