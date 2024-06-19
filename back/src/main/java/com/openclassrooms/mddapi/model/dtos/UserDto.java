package com.openclassrooms.mddapi.model.dtos;

import com.openclassrooms.mddapi.model.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class UserDto {

        private Long id;
        private String userName;
        private String email;
        private String password;
        private String token;
        private Timestamp created_at;
        private Timestamp updated_at;
        public static UserDto convertToDto (UserEntity userEntity){
            UserDto userDto = new UserDto();
            userDto.setId(userEntity.getId());
            userDto.setUserName(userEntity.getUsername());
            userDto.setEmail(userEntity.getEmail());
            userDto.setPassword(userEntity.getPassword());
            userDto.setToken(userEntity.getToken());
            userDto.setCreated_at(userEntity.getCreated_at());
            userDto.setUpdated_at(userEntity.getUpdated_at());

            return userDto;
        }

    }