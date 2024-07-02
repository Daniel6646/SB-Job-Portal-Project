package com.luv2code.jobportal.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luv2code.jobportal.entity.JobSeekerProfile;
import com.luv2code.jobportal.entity.RecruiterProfile;
import com.luv2code.jobportal.entity.Users;
import com.luv2code.jobportal.repository.JobSeekerProfileRepository;
import com.luv2code.jobportal.repository.RecruiterProfileRepository;
import com.luv2code.jobportal.repository.UserRepository;

@Service
public class UsersService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	JobSeekerProfileRepository jobSeekerProfileRepository;

	@Autowired
	RecruiterProfileRepository recruiterProfileRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	//public Users addNew(Users users) {
	public Users addNew(Users users) {
	
	users.setActive(true);
	users.setRegistrationDate(new Date(System.currentTimeMillis()));
	
	//during registeration encrypt user password.
	users.setPassword(passwordEncoder.encode(users.getPassword()));
	
	Users savedUser =  userRepository.save(users);
	int userTypeId =users.getUserTypeId().getUserTypeId(); 
	//Hi Rajan,Your explanation is correct:
	//users.getUserTypeId() : Retrieves the UsersType object associated with the Users entity. This is due to the @ManyToOne relationship defined in the Users entity.
	//users.getUserTypeId().getUserTypeId(): Retrieves the userTypeId value from the UsersType object.
	
	//id 1 means recruiter
	if(userTypeId  == 1) {
		
		recruiterProfileRepository.save(new RecruiterProfile(savedUser));
	}
	else {
		jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
	}
	
	 //userRepository.save(users);
	return savedUser;
	
	}

	public Object getCurrentUserProfile() {

 		Authentication authentication = SecurityContextHolder.getContext()
 				.getAuthentication();
	
	if(! (authentication instanceof AnonymousAuthenticationToken)) {
		
		String userName = authentication.getName();
		Users users = userRepository.findByEmail(userName).
		orElseThrow(()-> new UsernameNotFoundException("Could not find user"));
		
		int userId = users.getUserId();
		
		if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
			
		RecruiterProfile recruiterProfile =
				recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());	
		
		return recruiterProfile;
		} 
		
		else {
			
			JobSeekerProfile jobSeekerProfile=
					jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());	
			
			return jobSeekerProfile;
		}
	}
 	return null;
	
	
	}

	public Users getCurrentUser() {

 		Authentication authentication = SecurityContextHolder.getContext()
 				.getAuthentication();
	
	if(! (authentication instanceof AnonymousAuthenticationToken)) {
		
		String userName = authentication.getName();
		Users users = userRepository.findByEmail(userName).
		orElseThrow(()-> new UsernameNotFoundException("Could not find user"));
		

		return users;
	}
	
	return null;
	}

	
}