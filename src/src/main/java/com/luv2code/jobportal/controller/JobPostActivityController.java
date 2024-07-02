package com.luv2code.jobportal.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.luv2code.jobportal.entity.JobPostActivity;
import com.luv2code.jobportal.entity.Users;
import com.luv2code.jobportal.service.JobPostActivityService;
import com.luv2code.jobportal.service.UsersService;

@Controller
public class JobPostActivityController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private JobPostActivityService jobPostActivityService;
	
	
	
	@GetMapping("/dashboard/")
	public String searchJobs(Model model) {
		
		Object currentUserProfile = usersService.getCurrentUserProfile();
		Authentication authentication =  SecurityContextHolder.getContext()
				.getAuthentication();
		
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			
			String userName =authentication.getName();
			model.addAttribute("username", userName);
		}
		
		model.addAttribute("user", currentUserProfile);
		
		return "dashboard";
	}
	
	
//method to show the form
//html form that has info for adding a job
	
	@GetMapping("/dashboard/add")
	public String addJobs(Model model) {
	
		model.addAttribute("jobPostActivity", new JobPostActivity());
		model.addAttribute("user", usersService.getCurrentUserProfile());
		System.out.println("inside /dashboard/add line 57");
		return "add-jobs";
	}
	
	
	@PostMapping("/dashboard/addNew")
	public String addNew(JobPostActivity jobPostActivity, Model model) {
		
	Users user = usersService.getCurrentUser();
	System.out.println("Inside /dashboard/new line 65");
	if(user != null) {
		
		jobPostActivity.setPostedById(user);
		System.out.println("Inside /dashboard/new line 69");

	}
	jobPostActivity.setPostedDate(new Date());
	model.addAttribute("jobPostActivity", jobPostActivity);
	System.out.println("Inside /dashboard/new line 74");

	jobPostActivityService.addNew(jobPostActivity);
	System.out.println("Inside /dashboard/new line 77");

	return "redirect:/dashboard/";
	
	}
	
	
}
