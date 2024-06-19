package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.model.dtos.SubjectDto;
import com.openclassrooms.mddapi.model.entities.Subject;
import com.openclassrooms.mddapi.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subject")
    public Map<String, List<Subject>> getSubject(SubjectDto subjectDto) {
        return Map.of("subject", subjectService.getSubject(subjectDto));
    }
}
