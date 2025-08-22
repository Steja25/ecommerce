package com.example.demo.dto;

import com.example.demo.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAuth {
	String userName;
	String email;
	String password;
	Role role;
	
	

}
