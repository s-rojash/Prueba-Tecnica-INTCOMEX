package com.prueba.controller;

import com.prueba.model.User;
import com.prueba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("User/")
    public ResponseEntity<User> post(@RequestBody User users) {
        users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
        this.userService.save(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }
}
