package com.grace.mall.service;

import com.grace.mall.dto.UserRequest;
import com.grace.mall.model.User;

public interface UserService {

    Integer register(UserRequest userRequest);
    User getUserById(Integer userId);
    void login(UserRequest userRequest);
}
