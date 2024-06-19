    package com.openclassrooms.mddapi.repositories;

    import com.openclassrooms.mddapi.model.entities.Subject;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface SubjectRepository extends JpaRepository <Subject, Long> {
        Subject findByTitle(String title);
    }
