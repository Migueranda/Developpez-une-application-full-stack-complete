package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.model.dtos.UserDto;
import com.openclassrooms.mddapi.model.dtos.SubjectDto;
import com.openclassrooms.mddapi.model.entities.UserEntity;
import com.openclassrooms.mddapi.model.entities.Subject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements EntityMapper<UserDto, UserEntity> {

    @Override
    public UserEntity toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        return UserEntity.builder()
                .id(dto.getId())
                .username(dto.getUserName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .token(dto.getToken())
                .created_at(dto.getCreated_at())
                .updated_at(dto.getUpdated_at())
                .build();
    }

    @Override
    public UserDto toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return UserDto.builder()
                .id(entity.getId())
                .userName(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .token(entity.getToken())
                .created_at(entity.getCreated_at())
                .updated_at(entity.getUpdated_at())
                .build();
    }

    public UserDto toDto(UserEntity entity, List<Subject> subjects) {
        if (entity == null) {
            return null;
        }

        List<SubjectDto> subjectDtos = subjects.stream()
                .map(this::toSubjectDto)
                .collect(Collectors.toList());

        return UserDto.builder()
                .id(entity.getId())
                .userName(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .token(entity.getToken())
                .created_at(entity.getCreated_at())
                .updated_at(entity.getUpdated_at())
                .subscription(subjectDtos)
                .build();
    }

    @Override
    public List<UserEntity> toEntity(List<UserDto> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> toDto(List<UserEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public SubjectDto toSubjectDto(Subject subject) {
        if (subject == null) {
            return null;
        }

        return SubjectDto.builder()
                .id(subject.getId())
                .title(subject.getTitle())
                .description(subject.getDescription())
                .build();
    }
}
