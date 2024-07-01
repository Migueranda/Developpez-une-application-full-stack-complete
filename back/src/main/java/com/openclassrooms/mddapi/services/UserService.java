package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.dtos.SubjectDto;
import com.openclassrooms.mddapi.model.dtos.UserDto;
import com.openclassrooms.mddapi.model.entities.Subject;
import com.openclassrooms.mddapi.model.entities.UserEntity;
import com.openclassrooms.mddapi.repositories.SubjectRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final SubjectRepository subjectRepository;
    @Autowired
    private UserMapper userMapper;

    public UserDto register(UserDto userDto){
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(userDto.getEmail());

        if (optionalUserEntity.isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        //Conversion du DTO en Entité
        UserEntity userEntity = userMapper.toEntity(userDto);

       // Sauvegarde de l'Utilisateur
        UserEntity savedUserEntity = userRepository.save(userEntity);

        return userMapper.toDto(savedUserEntity);
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

        return userMapper.toDto(userEntity);
    }

    public UserDto getUser(final Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserEntity not found for id: " + id));

//       List<Subject> subjects = subjectRepository.findByUsers_Id(id);

        List<Subject> subjects = subjectRepository.findByUsers_Id(id);

//        subjects.forEach(subject -> {
//            System.out.println("Subject ID: " + subject.getId());
//            System.out.println("Subject Title: " + subject.getTitle());
//            System.out.println("Subject Description: " + subject.getDescription());
//        });
//        System.out.println("Subject all: " + subjects);
//
        return userMapper.toDto(userEntity, subjects);
        //return userMapper.toDto(userEntity);
    }

    public UserDto updateUser (Long id, UserDto userDto){

        UserEntity existingUserEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserEntity not found for id: " + id));

        existingUserEntity.setEmail(userDto.getEmail());
        existingUserEntity.setUsername(userDto.getUserName());
        existingUserEntity.setPassword(userDto.getPassword());

        UserEntity updatedUserEntity = userRepository.save(existingUserEntity);
        return userMapper.toDto(updatedUserEntity);

    }



}
