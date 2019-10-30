package com.backend.blog.Repositories;

import com.backend.blog.Entities.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByCreatorId(Long id);
}