package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.model.dtos.PostDto;

import com.openclassrooms.mddapi.model.entities.PostEntity;
import com.openclassrooms.mddapi.model.entities.Subject;
import com.openclassrooms.mddapi.model.entities.UserEntity;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.repositories.SubjectRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@Service
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final SubjectRepository subjectRepository;

    @Autowired
    private PostMapper postMapper;


    @Autowired
    private UserRepository userRepository;

    public List<PostDto> getAllPost(PostDto postDto, String sortBy, String order) {
        Sort.Direction direction;
        try {
            direction = Sort.Direction.valueOf(order.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order value: " + order);
        }

        Sort sort = Sort.by(direction, sortBy);
        Iterable<PostEntity> posts = postRepository.findAll(sort);

        List<PostEntity> postEntities = StreamSupport.stream(posts.spliterator(), false)
                .collect(Collectors.toList());

        return postEntities.stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    public PostDto createPost(PostDto postDto) {
        Optional<Subject> themeOptional = subjectRepository.findById(postDto.getThemeId());
        if (!themeOptional.isPresent()) {
            throw new RuntimeException("Theme not found");
        }

        Optional<UserEntity> userOptional = userRepository.findById(postDto.getUserId());
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }

        PostEntity postEntity = postMapper.toEntity(postDto);
        postEntity.setTheme(themeOptional.get());
        postEntity.setUser(userOptional.get());
        postEntity.setDate(new Timestamp(System.currentTimeMillis()));

        PostEntity savedPostEntity = postRepository.save(postEntity);
        return postMapper.toDto(savedPostEntity);
    }


    public PostDto getPostById(Long id) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(id);
        if (!postEntityOptional.isPresent()) {
            throw new EntityNotFoundException("Post not found with id " + id);
        }

        PostEntity postEntity = postEntityOptional.get();
        return postMapper.toDto(postEntity);
    }


}
