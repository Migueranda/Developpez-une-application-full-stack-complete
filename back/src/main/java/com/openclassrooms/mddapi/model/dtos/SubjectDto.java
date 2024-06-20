package com.openclassrooms.mddapi.model.dtos;

import com.openclassrooms.mddapi.model.entities.Subject;
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
public class SubjectDto {
    private Long id;
    private String title;
    private String description;

    public static SubjectDto convertToDto(Subject subject) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(subject.getId());
        subjectDto.setTitle(subject.getTitle());
        subjectDto.setDescription(subject.getDescription());
        return subjectDto;
    }
}
