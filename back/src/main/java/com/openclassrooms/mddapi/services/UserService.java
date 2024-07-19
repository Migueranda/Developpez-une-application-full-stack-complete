package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.AppException;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.dtos.CredentialsDto;
import com.openclassrooms.mddapi.model.dtos.SignUpDto;
import com.openclassrooms.mddapi.model.dtos.UserDto;
import com.openclassrooms.mddapi.model.entities.Subject;
import com.openclassrooms.mddapi.model.entities.UserEntity;
import com.openclassrooms.mddapi.repositories.SubjectRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, SubjectRepository subjectRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDto register(SignUpDto signUpDto){
        Optional<UserEntity> oUser = userRepository.findByEmail(signUpDto.email());
        if (oUser.isPresent()){
            throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userMapper.signUpToUser(signUpDto);// A verifier
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));

        UserEntity savedUser = userRepository.save(user);
        System.out.println("User registered successfully with email: " + signUpDto.email());
        return userMapper.toDto(savedUser);
    }


    public UserDto login(CredentialsDto credentialsDto){
        UserEntity user = userRepository.findByEmail(credentialsDto.email())
                .orElseThrow(() -> new AppException("Unkown user", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()),
                user.getPassword())) {
            return userMapper.toDto(user);
        }
        throw new AppException("Invalide password",HttpStatus.BAD_REQUEST);
    }

    public UserDto getUser(final Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserEntity not found for id: " + id));
        List<Subject> subjects = subjectRepository.findByUsers_Id(id);

        return userMapper.toDto(userEntity, subjects);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        UserEntity existingUserEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserEntity not found for id: " + id));

        existingUserEntity.setEmail(userDto.getEmail());
        existingUserEntity.setUserName(userDto.getUserName());

        if (!userDto.getPassword().equals(existingUserEntity.getPassword())) {
            existingUserEntity.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
        }

        UserEntity updatedUserEntity = userRepository.save(existingUserEntity);
        return userMapper.toDto(updatedUserEntity);
    }
}




