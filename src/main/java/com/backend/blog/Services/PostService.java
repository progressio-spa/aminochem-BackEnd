package com.backend.blog.Services;

import com.backend.blog.Entities.Post;
import com.backend.blog.Repositories.PostRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class PostService{

	@Autowired
	private PostRepository postRepository;

	public List<Post> getAllPosts(){
		return postRepository.findAll();
	}

	public void insert(Post post){
		postRepository.save(post);
	}
}