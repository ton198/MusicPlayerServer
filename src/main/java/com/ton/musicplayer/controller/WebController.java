package com.ton.musicplayer.controller;


import com.ton.musicplayer.entity.*;
import com.ton.musicplayer.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;


@RestController
public class WebController {

    private final MusicTransferService musicTransferService;
    private final UserService userService;
    private final MusicSearchingService musicSearchingService;
    private final CaptchaService captchaService;
    private final MusicOperatingService musicOperatingService;
    private final Logger logger;

    @Autowired
    public WebController(MusicTransferService musicTransferService,
                         UserService userService,
                         MusicSearchingService musicSearchingService,
                         CaptchaService captchaService,
                         MusicOperatingService musicOperatingService) {
        this.captchaService = captchaService;
        this.musicTransferService = musicTransferService;
        this.userService = userService;
        this.musicSearchingService = musicSearchingService;
        this.musicOperatingService = musicOperatingService;

        logger = LoggerFactory.getLogger(this.getClass());
    }

    @PostMapping("/post/login")
    public BasicErrorResponse login(HttpServletRequest httpServletRequest, @RequestBody LoginRequest loginRequest) {
        String userId = userService.getUserIdByIdentifier(loginRequest.getIdentifier());
        BasicErrorResponse loginResponse = userService.loginByUserId(userId, loginRequest.getPassword());
        if ("NONE".equals(loginResponse.getErr())) {
            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute("USER_ID", userId);
        }
        return loginResponse;
    }

    @PostMapping("/post/register")
    public BasicErrorResponse register(@RequestBody RegisterRequest request) {
        if (!captchaService.verify(request.getCaptchaId(), request.getCaptchaText())) {
            return new BasicErrorResponse("WRONG_CAPTCHA");
        }
        return userService.register(request);
    }

    @PostMapping("/post/download")
    public MusicDownloadResponse download(HttpServletRequest httpServletRequest, @RequestBody DownloadRequest downloadRequest) {
        HttpSession session = httpServletRequest.getSession();
        return musicTransferService.DownloadMusic(downloadRequest.getMusicId(), (String)session.getAttribute("USER_ID"));
    }

    @PostMapping(value = "/post/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BasicErrorResponse upload(HttpServletRequest httpServletRequest,
                                     @ModelAttribute UploadRequest uploadRequest
    ) {
        HttpSession session = httpServletRequest.getSession(false);
        if (!uploadRequest.validRequest()) return new BasicErrorResponse("INVALID_REQUEST");
        return musicTransferService.UploadMusic(uploadRequest, (String)session.getAttribute("USER_ID"));
    }

    @PostMapping("/post/search")
    public MusicSearchingResponse searchMusics(HttpServletRequest httpServletRequest, @RequestBody MusicSearchingRequest musicSearchingRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        return musicSearchingService.searchMusics(musicSearchingRequest, (String)session.getAttribute("USER_ID"));
    }

    @PostMapping("/post/captcha")
    public void getCaptcha(@RequestBody Map<String, String> body, HttpServletResponse response) {
        CaptchaGetRequest request = new CaptchaGetRequest(body.get("request_id"));

        byte[] data = captchaService.getCaptcha(request);
        if (data != null) {
            try {
                response.setContentType("image/jpeg");
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(data);
                outputStream.flush();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        } else {
            logger.error("captcha is null");
        }
    }

    @PostMapping("/post/add-favorite")
    public BasicErrorResponse addFavorites(HttpSession session, @RequestBody FavoritesOperatingRequest favoritesOperatingRequest) {
        if (!favoritesOperatingRequest.validRequest()) return new BasicErrorResponse("INVALID_REQUEST");
        return musicOperatingService.addToFavorites(new FavoritesOperatingPrivateRequest((String)session.getAttribute("USER_ID"), favoritesOperatingRequest.getMusicId()));
    }

    @PostMapping("/post/remove-favorite")
    public BasicErrorResponse removeFavorites(HttpSession session, @RequestBody FavoritesOperatingRequest favoritesOperatingRequest) {
        if (!favoritesOperatingRequest.validRequest()) return new BasicErrorResponse("INVALID_REQUEST");
        return musicOperatingService.removeFromFavorites(new FavoritesOperatingPrivateRequest((String)session.getAttribute("USER_ID"), favoritesOperatingRequest.getMusicId()));
    }

    @PostMapping("/post/check-favorite")
    public BasicErrorResponse checkFavorites(HttpSession session, @RequestBody FavoritesOperatingRequest favoritesOperatingRequest) {
        if (!favoritesOperatingRequest.validRequest()) return new BasicErrorResponse("INVALID_REQUEST");
        return musicOperatingService.checkMusicExistsInFavorites(new FavoritesOperatingPrivateRequest((String)session.getAttribute("USER_ID"), favoritesOperatingRequest.getMusicId()));
    }



    @ResponseBody
    @PostMapping("/post/test")
    public void test(@ModelAttribute UploadRequest param1) {
        logger.info(param1.toString());
    }

    @PostMapping("/")
    public void rootPage(HttpServletRequest request) {
        logger.info(String.valueOf(request.getRequestURL()));
    }
}
