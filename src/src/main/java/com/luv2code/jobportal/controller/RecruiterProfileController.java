package com.luv2code.jobportal.controller;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.luv2code.jobportal.entity.RecruiterProfile;
import com.luv2code.jobportal.entity.Users;
import com.luv2code.jobportal.repository.UserRepository;
import com.luv2code.jobportal.service.RecruiterProfileService;
import com.luv2code.jobportal.util.FileUploadUtil;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RecruiterProfileService recruiterProfileService;
	
	@GetMapping("/")
	public String recruiterProfile(Model model) {
		
		Authentication authentication =  SecurityContextHolder.getContext()
				.getAuthentication();
		
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			
			String currentUserName = authentication.getName();
			Users users = userRepository.findByEmail(currentUserName).orElseThrow(
					() -> new  UsernameNotFoundException("Could not find user"));
		
			Optional<RecruiterProfile> recruiterProfile =  recruiterProfileService.getOne(users.getUserId());
		
			if( !recruiterProfile.isEmpty()) {
				
				model.addAttribute("profile", recruiterProfile.get());
			}
		
		}
		
		return "recruiter_profile";		
	}

	//Purpose of this method : Associating image file upload with that given user.
	@PostMapping("/addNew")
	public String addNew(RecruiterProfile recruiterProfile,
			@RequestParam("image") MultipartFile multiPartFile, Model model  ) {
		
		// to get user from db
		Authentication authentication =  SecurityContextHolder.getContext()
				.getAuthentication();
		
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			
			String currentUserName = authentication.getName();
			Users users = userRepository.findByEmail(currentUserName).orElseThrow(
					() -> new  UsernameNotFoundException("Could not find user"));
			
			//associate recruiter profile with exisitng user account
			recruiterProfile.setUserId(users);
			recruiterProfile.setUserAccountId(users.getUserId());
			model.addAttribute("profile", recruiterProfile);
			
			String fileName = "";
			
			if(!multiPartFile.getOriginalFilename().equals("")) {
				
			fileName = StringUtils.cleanPath(Objects.requireNonNull(multiPartFile.getOriginalFilename()));
			//set image name in recruiter profile
			recruiterProfile.setProfilePhoto(fileName);
			
			
			//save recruiter profile to database
			RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);

			String uploadDir = "photos/recruiter"+savedUser.getUserAccountId();
			
			try {
			
			//Read profile image from request - multipartfile
			//Save image on server in directory photos/recruiter
			FileUploadUtil.saveFile(uploadDir, fileName, multiPartFile);
			
			}
			
			catch(Exception e) {
				e.printStackTrace();
			}
			
			}
		}
		return "redirect:/dashboard/";
	
	}
	
	
}
