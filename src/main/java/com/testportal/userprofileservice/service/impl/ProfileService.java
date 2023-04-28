package com.testportal.userprofileservice.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testportal.userprofileservice.dto.UserDto;
import com.testportal.userprofileservice.service.UserProfileService;

@Service
public class ProfileService {

	@Autowired
	private UserProfileService userHelper;

	@Autowired
	private ModelMapper mapper;

	public UserDto createNewUser(UserDto user) {
		UserDto userDto = null;
		try {
//			UserProfile u = userHelper.createUser(mapper.map(user, UserProfile.class), null);
//			mapper.map(u, UserDto.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
}
