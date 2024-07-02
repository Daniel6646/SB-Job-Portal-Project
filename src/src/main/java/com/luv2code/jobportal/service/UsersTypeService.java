package com.luv2code.jobportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.jobportal.entity.UsersType;
import com.luv2code.jobportal.repository.UsersTypeRepository;

@Service
public class UsersTypeService {

	//chad uses constructor type injection
	//	public UsersTypeService(UsersTypeRepository userTypeRepository) {
	//	super();
	//	this.userTypeRepository = userTypeRepository;
	//}
	
	
	@Autowired
	UsersTypeRepository userTypeRepository;


	public List<UsersType> getAll() {
		
		return userTypeRepository.findAll();
	}
	
	
}
