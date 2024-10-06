package com.grace.mall.service.impl;

import com.grace.mall.dao.UserDao;
import com.grace.mall.dto.UserRequest;
import com.grace.mall.model.User;
import com.grace.mall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRequest userRequest) {
        User user = userDao.getUserByEmail(userRequest.getEmail());
        if (user != null){
            log.warn("帳號 email {} 已存在" , userRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String hashedPasswd = DigestUtils.md5DigestAsHex(userRequest.getPassword().getBytes());
        userRequest.setPassword(hashedPasswd);

        return userDao.register(userRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public void login(UserRequest userRequest) {
        User user = userDao.getUserByEmail(userRequest.getEmail());
        if (user == null) {
            log.warn("email {} 不存在", userRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {

            if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(userRequest.getPassword().getBytes()))) {
                log.warn("email {} 密碼錯誤 ", userRequest.getEmail());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }

    }
}
