package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.model.dtos.CommentDto;
import com.openclassrooms.mddapi.model.entities.CommentEntity;
import com.openclassrooms.mddapi.model.entities.PostEntity;
import com.openclassrooms.mddapi.model.entities.UserEntity;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserRepository userRepository;

    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<CommentEntity> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    private CommentDto convertToDto(CommentEntity entity) {
        UserEntity user = entity.getUser();
        String userName = user != null ? user.getUserName() : "Utilisateur inconnu";

        return CommentDto.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .description(entity.getDescription())
                .date(entity.getDate())
                .postId(entity.getPost() != null ? entity.getPost().getId() : null)
                .userName(userName)
                .build();
    }

    public CommentDto addComment(Long postId, CommentDto commentDto) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));
        if (commentDto.getUserId() == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        UserEntity user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + commentDto.getUserId()));


        CommentEntity commentEntity = commentMapper.toEntity(commentDto);
        commentEntity.setPost(post);
        commentEntity.setUser(user);
        commentEntity.setDate(new Date());


        CommentEntity savedComment = commentRepository.save(commentEntity);

        CommentDto resultDto = commentMapper.toDto(savedComment);
        resultDto.setUserName(user.getUserName());
        return resultDto;
    }

}
