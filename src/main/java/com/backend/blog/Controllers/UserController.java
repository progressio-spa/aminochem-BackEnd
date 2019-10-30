package com.backend.blog.Controllers;

import com.backend.blog.Services.UserService;
import com.backend.blog.Entities.Role;
import com.backend.blog.Entities.User;
import com.backend.blog.Pojos.UserRegistration;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Arrays;

@RestController
public class UserController{

	@Autowired
	private UserService userService;

	@PostMapping(value = "/register")
	public String register(@RequestBody UserRegistration userRegistration){
		if (! userRegistration.getPassword().equals(userRegistration.getPasswordConfirmation()))
			return "Passwords doesn't match";
		else if (userService.getUser(userRegistration.getUsername()) != null)
			return "The user already exists!";

		// Here must be an input sanitization, i'm not sure what it means

		userService.save(new User(
			userRegistration.getUsername(),
			userRegistration.getPassword(),
			Arrays.asList(new Role("USER")),
			1
			));

		return "User created sucessfully";
	}

	@GetMapping(value = "/users")
	public List<User> users(){
		return userService.getAllUsers();
	}
}