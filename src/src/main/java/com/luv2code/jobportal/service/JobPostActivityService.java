package com.luv2code.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.jobportal.entity.JobPostActivity;
import com.luv2code.jobportal.repository.JobPostActivityRepository;

@Service
public class JobPostActivityService {

	
	@Autowired
	public JobPostActivityRepository jobPostActivityRepository;

	
	public JobPostActivity addNew(JobPostActivity jobPostActivity) {
		
		return jobPostActivityRepository.save(jobPostActivity);
	}
		
}
