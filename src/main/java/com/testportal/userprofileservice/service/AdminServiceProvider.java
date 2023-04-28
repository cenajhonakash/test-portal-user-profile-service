package com.testportal.userprofileservice.service;

import java.util.List;

import com.testportal.userprofileservice.entity.UserProfile;

public interface AdminServiceProvider {

	public List<UserProfile> getAllNormalAuthorityUserForAdmin(Long userId);
}
