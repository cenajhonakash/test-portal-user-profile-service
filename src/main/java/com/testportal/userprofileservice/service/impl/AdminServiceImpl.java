package com.testportal.userprofileservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.testportal.userprofileservice.entity.UserProfile;
import com.testportal.userprofileservice.service.AdminServiceProvider;

@Service
public class AdminServiceImpl implements AdminServiceProvider {

	@Override
	public List<UserProfile> getAllNormalAuthorityUserForAdmin(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
