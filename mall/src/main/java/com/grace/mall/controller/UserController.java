package com.grace.mall.controller;

import com.grace.mall.dto.UserRequest;
import com.grace.mall.model.User;
import com.grace.mall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRequest userRequest){
       Integer userId = userService.register(userRequest);
       User user = userService.getUserById(userId);
       return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserRequest userRequest){
        userService.login(userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
