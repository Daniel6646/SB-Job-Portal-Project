package com.luv2code.jobportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.luv2code.jobportal.entity.Users;
import com.luv2code.jobportal.entity.UsersType;
import com.luv2code.jobportal.service.UsersService;
import com.luv2code.jobportal.service.UsersTypeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class UsersController {

	@Autowired
	UsersTypeService userTypeService;

	@Autowired
	UsersService userService;
	
	// chad used constructor type injection	
	//	public UsersController(UsersTypeService userTypeService) {
	//		super();
	//		this.userTypeService = userTypeService;
	//	}
	//	
	
	@GetMapping("/register")
	public String register(Model model) {
		
	List<UsersType> usersTypes = userTypeService.getAll();
	model.addAttribute("getAllTypes", usersTypes);
	model.addAttribute("user", new Users());//empty user object just so we can have a instance for our form data.
	System.out.println("Inside controller user obj VALUES:: "+new Users());
	return "register";
	}
	
	@PostMapping("/register/new")
	public String userRegisteration(@Valid Users users) {
	
		System.out.println("Users OBJ VALUES:: "+users);
		userService.addNew(users);
		System.out.println("Users OBJ VALUES:: line 54 "+users);
		return "redirect:/dashboard/";
		// return "dashboard";
		
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		
	 Authentication authentication = SecurityContextHolder.getContext()
			 .getAuthentication();
	
	if(authentication != null) {
		
		new SecurityContextLogoutHandler().logout(request, response, authentication);
	}
	
	return "redirect:/";
	
	}
	
}
