package com.ton.musicplayer.service.impl;

import com.ton.musicplayer.entity.*;
import com.ton.musicplayer.service.DatabaseService;
import com.ton.musicplayer.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    DatabaseService databaseService;
    Logger logger;

    @Autowired
    public UserServiceImpl(DatabaseService databaseService) {
        logger = LoggerFactory.getLogger(this.getClass());
        this.databaseService = databaseService;
    }

    @Override
    public BasicErrorResponse loginByUserId(String userId, String password) {
        if (userId == null) {
            return new BasicErrorResponse("NO_SUCH_USER");
        }
        if (databaseService.checkPassword(userId, password)) {
            return new BasicErrorResponse("NONE");
        } else {
            return new BasicErrorResponse("WRONG_PASSWORD");
        }
    }

    @Override
    public String getUserIdByIdentifier(String identifier) {
        String userId;

        userId = databaseService.getUserIdByUsername(identifier);
        if (userId != null) return userId;

        userId = databaseService.getUserIdByEmail(identifier);
        if (userId != null) return userId;

        userId = databaseService.getUserIdByPhoneNumber(identifier);
        if (userId != null) return userId;

        return null;
    }

    @Override
    public BasicErrorResponse register(RegisterRequest request) {
        if (!request.validRequest()) {
            return new BasicErrorResponse("INVALID_REQUEST");
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(request.getEmail());
        userInfo.setPassword(request.getPassword());
        userInfo.setPhoneNumber(request.getPhoneNumber());
        userInfo.setUsername(request.getUsername());



        userInfo.setUserId(UUID.randomUUID().toString());
        databaseService.addUser(userInfo);
        return new BasicErrorResponse("NONE");
    }
}
