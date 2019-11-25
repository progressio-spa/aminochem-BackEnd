package com.backend.blog.Controllers;

import com.backend.blog.Services.UserService;
import com.backend.blog.Services.EmailService;
import com.backend.blog.Entities.Role;
import com.backend.blog.Entities.User;
import com.backend.blog.Entities.PasswordResetToken;
import com.backend.blog.Pojos.UserRegistration;
import com.backend.blog.dto.UsernameRecoveryRequest;
import com.backend.blog.dto.PasswordResetDTO;
import com.backend.blog.Repositories.PasswordResetTokenRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.UUID;

import org.springframework.ui.Model;

@RestController
public class UserController{

    @Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordResetTokenRepository tokenRepository;

	private boolean isValid(String email) {
		return email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
	}

	@PostMapping(value = "/register")
	public String register(@RequestBody UserRegistration userRegistration){
		if (! userRegistration.getPassword().equals(userRegistration.getPasswordConfirmation()))
			return "Passwords doesn't match";
		else if (! isValid(userRegistration.getUsername()))
			return "The username isn't valid.";
		else if (! userRegistration.getUsername().equals(userRegistration.getUsernameConfirmation()))
			return "The username doesn't match";
		else if (userService.getUser(userRegistration.getUsername()) != null)
			return "The user already exists!";

		// Here begins the input sanitization.

		if (userRegistration.getName() == null)
			return "The name can not be blank!";
		if (userRegistration.getLastname() == null)
			return "The lastname can not be blank!";

		// Here ends the input sanitization.

		userService.save(new User(
			userRegistration.getName(),
			userRegistration.getLastname(),
			userRegistration.getUsername(),
			userRegistration.getPassword(),
			Arrays.asList(new Role("USER")),
			1
			));

		String[] email = {userRegistration.getUsername()};

		emailService.sendEmail("Administrador", "Registro exitoso!", "Su cuenta se ha creado con éxito, sus credenciales son:\n\nUsuario: " + userRegistration.getUsername() + "\nContraseña: " + userRegistration.getPassword(), email);
		return "User created sucessfully";
	}

	@PostMapping(value = "/forgotPassword")
	public String forgotPassword(@RequestBody UsernameRecoveryRequest userRequest, HttpServletRequest request){
		if (userRequest.getUsername() != null){
			if (! isValid(userRequest.getUsername()))
				return "El usuario ingresado no tiene el formato correcto.";

			User user = userService.getUser(userRequest.getUsername());

			if (user == null)
				return "No se ha encontrado al usuario dentro de nuestros registros.";

			String[] email = {userRequest.getUsername()};

			PasswordResetToken token = new PasswordResetToken();
			token.setToken(UUID.randomUUID().toString());
			token.setUser(user);
			token.setExpiryDate(30); // El token tendrá una validez de 30 minutos.
			tokenRepository.save(token);

			String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/resetPassword?token=" + token.getToken();

			emailService.sendEmail("no-reply", "Recupera tu contraseña", "Se ha solicitado un cambio de contraseña. Para continuar con el proceso, debes continuar en el siguiente link: " + url + "\n\nSi no fue usted, puede ignorar este correo.", email);

			return "Email enviado de forma exitosa";
		}

		return "No se han ingresado los datos necesarios.";
	}

	@GetMapping(value = "/resetPassword")
	public String displayResetPasswordPage(@RequestParam(required = false) String token){
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null){
            return "El token no fue enviado."
        } else if (resetToken.isExpired()){
            return "El token ya expiró. Intente nuevamente."
        } else {
            return "El token está perfecto."
        }
	}

	@PostMapping(value = "/resetPassword")
	@Transactional
	public String handlePasswordReset(@RequestBody PasswordResetDTO form){
		PasswordResetToken token = tokenRepository.findByToken(form.getToken());

		if (form.getPassword() == null || form.getConfirmPassword() == null){
			return "no se ha ingresado la nueva contraseña.";
		}



        if (token == null){
            return "El token no fue enviado";
        } else if (token.isExpired()){
            return "El token ya expiró";
        } else {
			User user = token.getUser();
			if (form.getPassword().equals(form.getConfirmPassword())){
				String updatedPassword = passwordEncoder.encode(form.getPassword());
				userService.updatePassword(updatedPassword, user.getUsername());
				tokenRepository.delete(token);
			}

			return "Las contraseñas no coinciden.";
        }
	}

	@GetMapping(value = "/users")
	public List<User> users(){
		return userService.getAllUsers();
	}
}