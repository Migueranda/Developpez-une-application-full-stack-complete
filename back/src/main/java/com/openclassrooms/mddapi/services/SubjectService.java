package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.mapper.SubjectMapper;
import com.openclassrooms.mddapi.model.dtos.SubjectDto;
import com.openclassrooms.mddapi.model.entities.Subject;
import com.openclassrooms.mddapi.repositories.SubjectRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@Service
public class SubjectService {
    @Autowired
    private final SubjectRepository subjectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectMapper subjectMapper;

    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    public List<Subject> getSubject(SubjectDto subjectDto) {
        Iterable<Subject> subjects = subjectRepository.findAll();

        List<Subject> result = StreamSupport.stream(subjects.spliterator(), false)
                .collect(Collectors.toList());

        return result;
    }


    public SubjectDto updateSubject (Long id, SubjectDto subjectDto){

        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found for id: " + id));

        existingSubject.setTitle(subjectDto.getTitle());
        existingSubject.setDescription(subjectDto.getDescription());

        Subject updatedSubject = subjectRepository.save(existingSubject);
        return subjectMapper.toDto(updatedSubject);

    }

}
