package com.testportal.userprofileservice.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.testportal.userprofileservice.dto.CredentialsDto;
import com.testportal.userprofileservice.dto.UserDto;
import com.testportal.userprofileservice.entity.UserProfile;
import com.testportal.userprofileservice.exception.InvalidCrentials;
import com.testportal.userprofileservice.exception.MissingMandatoryAttribute;
import com.testportal.userprofileservice.exception.ResourceAlreadyPresent;
import com.testportal.userprofileservice.exception.UserNotFoundException;
import com.testportal.userprofileservice.helper.UserProfileServiceHelper;
import com.testportal.userprofileservice.repository.UserProfileRepository;

@Component
public class UserProfileService {

	private Logger log = LoggerFactory.getLogger(ProfileServiceProvider.class);

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserProfileServiceHelper userProfileServiceHelper;

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
	public UserDto createNewUser(UserDto userDto, MultipartFile image) {

		try {
			userProfileServiceHelper.validateUserDetails(userDto, true);
		} catch (MissingMandatoryAttribute e) {
			return null;
		}
		UserProfile newUser = mapper.map(userDto, UserProfile.class);
		UserProfile adddedUser = null;
		if (userDto.getRole() != null && userDto.getRole().getRoles() != null && userDto.getRole().getRoles().length != 0) {
			/*
			 * validate whether user is getting created with role (ADMIN,SUPER ADMIN) by an SUPER ADMIN not by other roles user orElse throw exception
			 */
		} else {
			try {
				adddedUser = userProfileServiceHelper.addUserWithRoles(newUser);
			} catch (ResourceAlreadyPresent e) {
				return null;
			}
		}
		return mapper.map(adddedUser, UserDto.class);
	}

	public UserProfile getUserByUserName(String userName) {
		if (userName != null) {
			Optional<UserProfile> u = userProfileRepository.findByUsername(userName);
			if (u.isPresent()) {
				return u.get();
			}
		}
		return null;
	}

	public void deleteUser(Long userID) {
		// TODO Auto-generated method stub

	}

	public UserDto updateUser(UserDto userDto, MultipartFile image) {
		try {
			userProfileServiceHelper.validateUserDetails(userDto, true);
		} catch (MissingMandatoryAttribute e) {
			return null;
		}
		return null;
	}

	public UserDto getUserById(Long id) throws UserNotFoundException {
		Optional<UserProfile> u = userProfileRepository.findById(id);
		if (u.isEmpty()) {
			log.error("user not found with id: {}", id);
			throw new UserNotFoundException();
		}
		return mapper.map(u.get(), UserDto.class);
	}

	public UserDto getUserByUsername(String userName) throws UserNotFoundException {
		if (userName != null) {
			Optional<UserProfile> u = userProfileRepository.findByUsername(userName);
			if (u.isPresent()) {
				return mapper.map(u.get(), UserDto.class);
			} else {
				log.error("user not found with username: {}", userName);
				throw new UserNotFoundException();
			}
		}
		return null;
	}

	public UserDto getUserByCredentials(CredentialsDto credentials) throws UserNotFoundException, InvalidCrentials {
		if (credentials != null && credentials.getPassword() != null && credentials.getPassword() != null) {
			Optional<UserProfile> u = userProfileRepository.findByUsernameAndPassword(credentials.getUsername(), credentials.getPassword());
			if (u.isPresent()) {
				return mapper.map(u.get(), UserDto.class);
			} else {
				log.error("user not found with username: {}", credentials.getUsername());
				throw new UserNotFoundException();
			}
		} else {
			throw new InvalidCrentials("Invalid Credentials!");
		}
	}
}
