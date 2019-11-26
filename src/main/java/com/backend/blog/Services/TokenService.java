package com.backend.blog.Services;

import com.backend.blog.Entities.PasswordResetToken;

import com.backend.blog.Repositories.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class TokenService{

	@Autowired
	private PasswordResetTokenRepository repo;

	public List<PasswordResetToken> getAllTokens(){
		return repo.findAll();
	}

	public void save(PasswordResetToken token){
		repo.save(token);
	}

	public PasswordResetToken findByToken(String token){
		return repo.findByToken(token);
	}

	public void delete(PasswordResetToken token){
		repo.delete(token);
	}
}