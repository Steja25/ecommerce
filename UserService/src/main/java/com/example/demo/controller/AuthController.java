package com.example.demo.controller;

import java.net.http.HttpResponse;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginAuth;
import com.example.demo.dto.RegisterAuth;
import com.example.demo.dto.ResponseAuth;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	UserRepository repo;
	@Autowired
	PasswordEncoder encode;
	@Autowired
	JwtUtil util;

	
	@PostMapping("/register")
	public ResponseEntity<String > register(@RequestBody RegisterAuth reg){
		User user=new User();
		user.setEmail(reg.getEmail());
		user.setUserName(reg.getUserName());
		user.setPassword(encode.encode(reg.getPassword()));
		user.setRole(reg.getRole());

		
		repo.save(user);
		
		return ResponseEntity.ok("Register Successfully");
		
		
	}
	 @GetMapping("/users/{id}")
	  public  User getUserById(@PathVariable Long id) {
		Optional<User> user= repo.findById(id);
		if(user.isPresent()) {
			User users=user.get();
			return users;
		}
		else {
			throw new RuntimeException("user not found");
		}
	 }
	
	@PostMapping("/login")
	public ResponseEntity<ResponseAuth> login(@RequestBody LoginAuth login){
//		
//	Authentication authentication=auth.authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));
//		
//		
		Optional<User> optional=repo.findByUserName(login.getUserName());
		
		if(optional.isPresent() ) {
			User user=optional.get();
			if(encode.matches(login.getPassword(), user.getPassword())) {
				String token=util.generateToken(login.getUserName());
				return ResponseEntity.ok(new ResponseAuth(token,"loginSuccessful"));
			}
		}
		
		return ResponseEntity.ok(new ResponseAuth(null,"username Or Password Invalid"));
	}
	
	
	

}
