package com.backend.blog;

import com.backend.blog.Entities.Role;
import com.backend.blog.Entities.User;
import com.backend.blog.Services.UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class BlogApplication {


    @Bean
    public CommandLineRunner setupDefaultUser(UserService service) {
        return args -> {
            service.save(new User(
                    "Usuario",                                          //name
                    "Administrador",                                    //lastname
                    "00.000.000-0",                                     //rut
                    "admin_username",                                   //username
                    "admin_password",                                   //password
					Arrays.asList(new Role("USER"), new Role("ADMIN")), //roles 
                    1                                                   //active
            ));
        };
    }
    
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }  

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
