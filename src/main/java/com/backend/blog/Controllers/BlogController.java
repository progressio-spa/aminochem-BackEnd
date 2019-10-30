package com.backend.blog.Controllers;

import org.springframework.security.core.userdetails.UserDetails;
import com.backend.blog.Services.UserService;
import com.backend.blog.Services.PostService;
import com.backend.blog.Entities.Post;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Date;

@RestController
public class BlogController{

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@GetMapping(value = "/")
	public String index(){
		return "index";
	}

	@GetMapping(value = "/posts")
	public List<Post> getAllPosts(){
		return postService.getAllPosts();
	}

	@PostMapping(value = "/post")
	public String publishPost(@RequestBody Post post){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}

		if (post.getDateCreated() == null)
			post.setDateCreated(new Date());

		post.setCreator(userService.getUser(username));
		postService.insert(post);
		return "Post was published";
	}

	@GetMapping(value = "/posts/{username}")
	public List<Post> postsByUsername(@PathVariable String username){
		 return postService.findByUser(userService.getUser(username));
	}
}