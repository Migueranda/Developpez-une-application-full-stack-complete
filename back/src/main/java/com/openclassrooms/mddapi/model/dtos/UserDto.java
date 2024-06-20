package com.openclassrooms.mddapi.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.openclassrooms.mddapi.model.entities.Subject;
import com.openclassrooms.mddapi.model.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class UserDto {

        private Long id;
        private String userName;
        private String email;
        private String password;
        private String token;
        private Timestamp created_at;
        private Timestamp updated_at;
        private List<SubjectDto> subscription;


        public static UserDto convertToDto (UserEntity userEntity, List<Subject> subjects){
            UserDto userDto = new UserDto();
            userDto.setId(userEntity.getId());
            userDto.setUserName(userEntity.getUsername());
            userDto.setEmail(userEntity.getEmail());
            userDto.setPassword(userEntity.getPassword());
            userDto.setToken(userEntity.getToken());
            userDto.setCreated_at(userEntity.getCreated_at());
            userDto.setUpdated_at(userEntity.getUpdated_at());

            userDto.setSubscription(subjects.stream()
                    .map(SubjectDto::convertToDto)
                    .collect(Collectors.toList()));

            return userDto;
        }

    }