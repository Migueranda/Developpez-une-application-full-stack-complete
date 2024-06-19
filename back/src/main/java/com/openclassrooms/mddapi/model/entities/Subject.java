package com.openclassrooms.mddapi.model.entities;

import com.openclassrooms.mddapi.model.dtos.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "SUBJECT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE_SUBJECT")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;
}
