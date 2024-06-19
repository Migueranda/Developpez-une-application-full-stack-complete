package com.openclassrooms.mddapi.model.dtos;

import com.openclassrooms.mddapi.model.entities.Subject;
import com.openclassrooms.mddapi.model.entities.UserEntity;

import java.sql.Timestamp;

public class SubjectDto {
    private Long id;
    private String title;
    private String email;
    private String description;

    public static SubjectDto convertToDto (Subject subject){
        SubjectDto subjectDto = new SubjectDto();
        subject.setId(subject.getId());
        subject.setTitle(subject.getTitle());
        subject.setDescription(subject.getDescription());

        return subjectDto;
    }
}
