package com.ton.musicplayer.config;

import com.ton.musicplayer.interceptor.CommonInterceptor;
import com.ton.musicplayer.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurator implements WebMvcConfigurer {


    private final SessionInterceptor sessionInterceptor;
    private final CommonInterceptor commonInterceptor;

    @Autowired
    public WebConfigurator(SessionInterceptor sessionInterceptor, CommonInterceptor commonInterceptor) {
        this.sessionInterceptor = sessionInterceptor;
        this.commonInterceptor = commonInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/post/*").excludePathPatterns("/post", "/post/login", "/post/register", "/post/captcha", "/post/test");
        registry.addInterceptor(commonInterceptor).addPathPatterns("/*");
    }
}
