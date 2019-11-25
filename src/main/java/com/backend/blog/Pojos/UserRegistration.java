package com.backend.blog.Pojos;

public class UserRegistration{
	private String name;
	private String lastname;
	// private String rut;
	private String username;
	private String usernameConfirmation;
	private String password;
	private String passwordConfirmation;

	public UserRegistration(){

	}

	public UserRegistration(String name, String lastname, /*String rut,*/ String username, String usernameConfirmation, String password, String passwordConfirmation){
		this.name = name;
		this.lastname = lastname;
		// this.rut = rut;
		this.username = username;
		this.usernameConfirmation = usernameConfirmation;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
	}

	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getLastname(){
		return this.lastname;
	}

	public void setLastname(String lastname){
		this.lastname = lastname;
	}

	// public String getRut(){
	// 	return this.rut;
	// }

	// public void setRut(String rut){
	// 	this.rut = rut;
	// }

	public String getUsername(){
		return this.username;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsernameConfirmation(){
		return this.usernameConfirmation;
	}

	public void setUsernameConfirmation(String usernameConfirmation){
		this.usernameConfirmation = usernameConfirmation;
	}

	public String getPassword(){
		return this.password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPasswordConfirmation(){
		return this.passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation){
		this.passwordConfirmation = passwordConfirmation;
	}
}