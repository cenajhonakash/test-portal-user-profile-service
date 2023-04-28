package com.testportal.userprofileservice.service;

import java.util.Set;

import com.testportal.userprofileservice.entity.UserProfile;
import com.testportal.userprofileservice.entity.UserRole;

public interface ProfileServiceProvider {
	public UserProfile createUser(UserProfile u, Set<UserRole> userRoles) throws Exception;

	public UserProfile getUserByUserName(String userName);

	public void deleteUser(Long userID);

	// public User updateUser(Long userId);

	public UserProfile updateUser(UserProfile u);
}
