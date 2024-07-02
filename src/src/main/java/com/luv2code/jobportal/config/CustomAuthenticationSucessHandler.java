package com.luv2code.jobportal.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSucessHandler implements AuthenticationSuccessHandler {

	//Once logged in sucessfully
	//Retrieve user object
	//Check roles for the user.
	// if jobseeker or recruiter role then send them to dashboard page
	
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String userName = userDetails.getUsername();
		System.out.println("The USERNAME "+userName+ " is logged in");
		
		//get roles for user and see if there is a match
		boolean hasJobSeekerRole = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("Job Seeker"));
	
		boolean hasRecruiterRole = authentication.getAuthorities().stream()
		.anyMatch(r -> r.getAuthority().equals("Recruiter"));
	
		if( hasJobSeekerRole || hasRecruiterRole) {
			response.sendRedirect("/dashboard/");
		}
		
	}

}
