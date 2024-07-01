package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.model.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends PagingAndSortingRepository<PostEntity, Long> {

    PostEntity save(PostEntity postEntity);

    Optional<PostEntity> findById(Long postId);
}
