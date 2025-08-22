package com.example.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtFilter extends OncePerRequestFilter{
	@Autowired
	JwtUtil util;

    @Autowired
    private UserDetailsService userDetailsService; // Spring Security service to load users

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		     String header=request.getHeader("Authorization");
		     String userName=null;
		     String token=null;
		     if(header !=null && header.startsWith("Bearer ")) {
		    	 token=header.substring(7);
		       userName=util.extractUsername(token);
		       if(userName!=null&& SecurityContextHolder.getContext().getAuthentication()==null) {
		    	          UserDetails user= userDetailsService.loadUserByUsername(userName);
		    	         
		    	          if(util.validateToken(token, user)) {
		    	        	  UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(userName,null,user.getAuthorities());
		   		    	   
		   		    	   SecurityContextHolder.getContext().setAuthentication(auth);
		    	          }
		    	          
		    	 
		       }
		    	 
		    	
		    	 
		    	 
		     }
		
	filterChain.doFilter(request, response);
	}

}
