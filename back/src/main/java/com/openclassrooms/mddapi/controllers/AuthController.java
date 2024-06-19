package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.model.dtos.UserDto;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@PostMapping("/register")
@RestController
public class AuthController {
    @Autowired
    private  UserService userService;
    @PostMapping("/register")
    public UserDto register(@RequestBody UserDto userDto){
        return  userService.register(userDto);
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody UserDto userDto){
        return userService.login(userDto);
    }
}
