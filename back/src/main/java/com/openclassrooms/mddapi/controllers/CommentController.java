package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.model.dtos.CommentDto;
import com.openclassrooms.mddapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
//@RequestMapping("/post/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentDto> addComment(@PathVariable Long postId, @RequestBody CommentDto commentDto) {
        CommentDto createdComment = commentService.addComment(postId, commentDto);
        return ResponseEntity.created(URI.create("/post/" + postId + "/comments/" + createdComment.getId())).body(createdComment);
    }
    @GetMapping("/post/{postId}/comment")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long postId) {
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}
