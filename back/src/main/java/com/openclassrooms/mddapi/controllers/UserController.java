package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.model.dtos.UserDto;
import com.openclassrooms.mddapi.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    @Autowired
   private UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        try {
            UserDto userDto = userService.getUser(id);
            return ResponseEntity.ok(userDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @Valid @RequestBody UserDto userDto){
        UserDto updatedUserEntity = userService.updateUser(Long.valueOf(id), userDto);
        return ResponseEntity.ok(updatedUserEntity);
    }
}
