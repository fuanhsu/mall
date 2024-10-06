package com.grace.mall.dao;

import com.grace.mall.dto.ProductRequestParam;
import com.grace.mall.dto.UserRequest;
import com.grace.mall.model.User;

public interface UserDao {
    User getUserById(Integer userId);
    Integer register(UserRequest userRequest);
    User getUserByEmail(String email);
}
