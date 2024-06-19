package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.model.dtos.UserDto;
import com.openclassrooms.mddapi.model.entities.UserEntity;
import com.openclassrooms.mddapi.repositories.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Data
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserRepository getUserRepository() {
        return userRepository;
    }

    public UserDto register(UserDto userDto){
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(userDto.getEmail());

        if (optionalUserEntity.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        //Conversion du DTO en Entité
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(userDto.getUserName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());

       // Sauvegarde de l'Utilisateur
        UserEntity savedUserEntity = userRepository.save(userEntity);

        userDto.setId(savedUserEntity.getId());

        return userDto;
       // return ResponseEntity.created(URI.create("/user/" + savedUserEntity.getId())).body(userDto).getBody();
    }

    public UserDto login(UserDto userDto){
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(userDto.getEmail());

        if (!optionalUserEntity.isPresent()) {
            throw new RuntimeException("Unkown user");
        }

        UserEntity userEntity = optionalUserEntity.get();

        // Vérifie le mot de passe
        if (!userEntity.getPassword().equals(userDto.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        //Retour des Détails de l'Utilisateur
        userDto.setId(userEntity.getId());
        userDto.setEmail(userEntity.getEmail());

        return userDto;
    }
}
