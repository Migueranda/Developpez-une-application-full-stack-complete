package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.model.dtos.CommentDto;
import com.openclassrooms.mddapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<?> addComment(@PathVariable String postId, @RequestBody CommentDto commentDto) {
        try {
            Long postID = Long.parseLong(postId);
            CommentDto createdComment = commentService.addComment(postID, commentDto);
            return ResponseEntity.created(URI.create("/post/" + postID + "/comments/" + createdComment.getId())).body(createdComment);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid post ID format");
        }
    }

    @GetMapping("/post/{postId}/comment")
    public ResponseEntity<?> getComments(@PathVariable String postId) {
        try {
            Long postID = Long.parseLong(postId);
            List<CommentDto> comments = commentService.getCommentsByPostId(postID);
            return ResponseEntity.ok(comments);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid post ID format");
        }
    }
}
