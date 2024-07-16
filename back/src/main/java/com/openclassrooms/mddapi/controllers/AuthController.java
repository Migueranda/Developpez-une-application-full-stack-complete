package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.model.dtos.UserDto;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@PostMapping("/register")
@RestController
public class AuthController {
    @Autowired
    private  UserService userService;


    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto){
        try {
            return ResponseEntity.ok(userService.register(userDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.login(userDto));
    }
//    @PostMapping("/register")
//    public UserDto register(@RequestBody UserDto userDto){
//        return  userService.register(userDto);
//    }
//
//    @PostMapping("/login")
//    public UserDto login(@RequestBody UserDto userDto){
//        return userService.login(userDto);
//    }
}
