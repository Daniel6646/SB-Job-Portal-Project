package com.luv2code.jobportal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.jobportal.entity.RecruiterProfile;
import com.luv2code.jobportal.repository.RecruiterProfileRepository;

@Service
public class RecruiterProfileService {

	@Autowired
	private RecruiterProfileRepository recruiterProfileRepository;
	
	
	public Optional<RecruiterProfile> getOne(Integer id) {
		
		return recruiterProfileRepository.findById(id);
	}


	public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {

		return recruiterProfileRepository.save(recruiterProfile);
	}
	
	
}
