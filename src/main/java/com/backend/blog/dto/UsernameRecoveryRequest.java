package com.backend.blog.dto;

public class UsernameRecoveryRequest{
	private String username;

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}
}