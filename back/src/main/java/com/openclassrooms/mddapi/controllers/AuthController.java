package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.AppException;
import com.openclassrooms.mddapi.configuration.UserAuthProvider;
import com.openclassrooms.mddapi.model.dtos.CredentialsDto;
import com.openclassrooms.mddapi.model.dtos.SignUpDto;
import com.openclassrooms.mddapi.model.dtos.UserDto;
import com.openclassrooms.mddapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class AuthController {
    @Autowired
    private  UserService userService;

    private final UserAuthProvider userAuthProvider;

    @PostMapping("/auth/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        try {
            UserDto user = userService.register(signUpDto);
            user.setToken(userAuthProvider.createToken(user));
            return ResponseEntity.created(URI.create("/user/" + user.getId())).body(user);
        } catch (AppException e) {
            System.out.println("Error during user registration: " + e.getMessage());
            return ResponseEntity.status(e.getHttpStatus()).body(null);
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto){
        UserDto user = userService.login(credentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }
}
