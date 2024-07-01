package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.model.dtos.PostDto;
import com.openclassrooms.mddapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/post")
    public Map<String, List<PostDto>> getAllPost(
            PostDto postDto,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String order) {
        return Map.of("post", postService.getAllPost(postDto, sortBy, order));
    }

    @PostMapping("/post")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto createdPost = postService.createPost(postDto);
        return ResponseEntity.created(URI.create("/post/" + createdPost.getId())).body(createdPost);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        PostDto postDto = postService.getPostById(id);
        return ResponseEntity.ok(postDto);
    }
}
