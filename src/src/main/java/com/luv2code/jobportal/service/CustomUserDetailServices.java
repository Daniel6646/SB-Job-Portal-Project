package com.luv2code.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.luv2code.jobportal.entity.Users;
import com.luv2code.jobportal.repository.UserRepository;
import com.luv2code.jobportal.util.CustomUserDetails;

@Service
public class CustomUserDetailServices implements UserDetailsService {

// implements UserDetailsService from spring security framework	

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	// tell spring security how to retrieve user from database
		
	Users user = userRepository.findByEmail(username).orElseThrow(
				() -> new UsernameNotFoundException("Could not find username"));
		
		return new CustomUserDetails(user);
	}

	
	
}
