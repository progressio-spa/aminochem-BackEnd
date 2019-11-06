package com.backend.blog.Controllers;

import com.backend.blog.Services.UserService;
import com.backend.blog.Entities.Role;
import com.backend.blog.Entities.User;
import com.backend.blog.Pojos.UserRegistration;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@RestController
public class UserController{

	@Autowired
	private UserService userService;

	private boolean rutValidation(int rut, int validator){
		int m = 0, s = 1;
		for (; rut != 0; rut /= 10) {
			s = (s + rut % 10 * (9 - m++ % 6)) % 11;
		}
		return validator == (char) (s != 0 ? s + 47 : 75);
	}

	private boolean rutValidation(String rutString){
		Pattern p;
		p = Pattern.compile("^([0-9]+-[0-9K])$");
		Matcher m = p.matcher(rutString);
		boolean b = m.matches();

		if (b){
			int rut = Integer.parseInt(rutString.split("-")[0]);
			String validator = rutString.substring(rutString.length() - 1, rutString.length());

			if (! rutValidation(rut, validator.toCharArray()[0]))
				return true;
			else
				return false;

		} else 
			return false;
	}

	private String usernameCreation(String name, String lastname, String rut){
		return name.toLowerCase() + "." + lastname.toLowerCase();
	}

	@PostMapping(value = "/register")
	public String register(@RequestBody UserRegistration userRegistration){
		if (! userRegistration.getPassword().equals(userRegistration.getPasswordConfirmation()))
			return "Passwords doesn't match";
		// else if (userService.getUser(userRegistration.getUsername()) != null)
		// 	return "The user already exists!";

		// Here must be an input sanitization.

		// Here begins the input sanitization.

		if (userRegistration.getName() == null)
			return "The name can not be blank!";
		if (userRegistration.getLastname() == null)
			return "The lastname can not be blank!";
		if (this.rutValidation(userRegistration.getRut()))
			return "The rut is incorrect.";




		// Here ends the input sanitization.

		userService.save(new User(
			userRegistration.getName(),
			userRegistration.getLastname(),
			userRegistration.getRut(),
			// userRegistration.getUsername(),
			this.usernameCreation(userRegistration.getName(), userRegistration.getLastname(), userRegistration.getRut()),
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