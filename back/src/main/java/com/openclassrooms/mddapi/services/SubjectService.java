package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.model.dtos.SubjectDto;
import com.openclassrooms.mddapi.model.entities.Subject;
import com.openclassrooms.mddapi.repositories.SubjectRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }
    public List<Subject> getSubject(SubjectDto subjectDto) {
        Iterable<Subject> subjects = subjectRepository.findAll();

        List<Subject> result = StreamSupport.stream(subjects.spliterator(), false)
                .collect(Collectors.toList());
//        System.out.println("Nombre de sujets : " + result.size());
//        result.forEach(subject -> System.out.println(subject.getTitle()));
        return result;
    }
}
