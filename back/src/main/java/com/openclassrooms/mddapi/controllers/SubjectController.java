package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.mapper.SubjectMapper;
import com.openclassrooms.mddapi.model.dtos.SubjectDto;
import com.openclassrooms.mddapi.model.entities.Subject;
import com.openclassrooms.mddapi.services.SubjectService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/subject/{id}")
    public ResponseEntity<?> updateSubject(@PathVariable("id") String id, @Valid @RequestBody SubjectDto subjectDto){
        SubjectDto updatedSubject = subjectService.updateSubject(Long.valueOf(id), subjectDto);
        return ResponseEntity.ok(updatedSubject);
    }
}
