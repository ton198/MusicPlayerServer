package com.ton.musicplayer.service;

import com.ton.musicplayer.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface UserService {

    BasicErrorResponse loginByUserId(String userId, String password);

    String getUserIdByIdentifier(String identifier);

    BasicErrorResponse register(RegisterRequest request);

}
