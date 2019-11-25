package com.backend.blog.Services;

import com.backend.blog.Repositories.UserRepository;
import com.backend.blog.Entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

import org.springframework.mail.MailSender;


@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
    }

    public User getUser(String username){
    	return repo.findByUsername(username);
    }

    public List<User> getAllUsers(){
        return repo.findAll();
    }

    public void updatePassword(String newPassword, String username){
        User userToModify = this.getUser(username);
        userToModify.setPassword(newPassword);
        repo.save(userToModify);
    }

}
