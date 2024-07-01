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

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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


    public CommentDto addComment(Long postId, CommentDto commentDto) {
        // Validate the post's existence
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        // Validate the user's existence
        if (commentDto.getUserId() == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        UserEntity user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + commentDto.getUserId()));

        // Convert DTO to Entity using the mapper
        CommentEntity commentEntity = commentMapper.toEntity(commentDto);
        commentEntity.setPost(post);
        commentEntity.setUser(user);
        commentEntity.setDate(new Timestamp(System.currentTimeMillis())); // Set current time as comment date

        // Save the comment entity
        CommentEntity savedComment = commentRepository.save(commentEntity);

        // Convert saved entity back to DTO and return
        return commentMapper.toDto(savedComment);
    }

    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<CommentEntity> comments = commentRepository.findByPostId(postId);
        return commentMapper.toDto(comments);
    }
}
