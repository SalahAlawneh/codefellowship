package com.salahcodefellowship.codefellowship.repository;

import com.salahcodefellowship.codefellowship.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
