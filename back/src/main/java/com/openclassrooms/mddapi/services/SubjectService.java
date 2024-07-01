package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.mapper.SubjectMapper;
import com.openclassrooms.mddapi.model.dtos.SubjectDto;
import com.openclassrooms.mddapi.model.entities.Subject;
import com.openclassrooms.mddapi.model.entities.UserEntity;
import com.openclassrooms.mddapi.repositories.SubjectRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
//        System.out.println("Nombre de sujets : " + result.size());
//        result.forEach(subject -> System.out.println(subject.getTitle()));
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

//    public void subscribe(Long id, Long userId) throws ChangeSetPersister.NotFoundException, BadRequestException {
//        // Récupération du sujet et d'utilisateur par son ID
//        Subject subject = this.subjectRepository.findById(id).orElse(null);
//        UserEntity user = this.userRepository.findById(userId).orElse(null);
//
//        // Vérification si le sujet ou l'utilisateur n'existent pas
//        if (subject == null || user == null) {
//            throw new ChangeSetPersister.NotFoundException();
//        }
//        // Vérification si l'utilisateur est déjà abonné au sujet
//        boolean alreadySubscribe = subject.getUsers.stream().anyMatch(o -> o.getUser().equals(userId));
//
//        // Si l'utilisateur est déjà abonné, lancer une exception
//        if(alreadySubscribe) {
//            throw new BadRequestException();
//        }
//        // Ajouter l'utilisateur à la liste des abonnés du sujet
//        subject.getUsers().add(user);
//        // Sauvegarder le sujet mis à jour dans le repository
//        this.subjectRepository.save(subject);
//
//    }
//
//    public void  unsubscribe(Long id, Long userId) throws ChangeSetPersister.NotFoundException, BadRequestException {
//        Subject subject = this.subjectRepository.findById(id).orElse(null);
//        if (subject == null) {
//            throw new ChangeSetPersister.NotFoundException();
//        }
//
//        boolean alreadySubscribe = subject.getUsers().stream().anyMatch(o -> o.getEmail().equals(userId));
//
//        if (!alreadySubscribe) {
//            throw new BadRequestException();
//        }
//
//        subject.setUsers(subject.getUsers().stream().filter(user -> !user.getId().equals(userId)).collect(Collectors.toList()));
//
//        this.subjectRepository.save(subject);
//    }


}
