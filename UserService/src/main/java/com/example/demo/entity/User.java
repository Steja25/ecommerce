package com.example.demo.entity;

import com.example.demo.enums.Role;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Data
@Setter
@Getter
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary key

	
	 @Column(name = "username", nullable = false)
	    private String userName;

	    @Column(nullable = false)
	    private String password;

	    @Column(nullable = false)
	    private String email;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private Role role;
	
	
	
	

}
