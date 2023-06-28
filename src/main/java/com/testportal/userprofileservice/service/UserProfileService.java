package com.testportal.userprofileservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.testportal.testportal.commonframework.dto.ApiResponse;
import com.testportal.testportal.commonframework.dto.ApiResponse.ApiResponseBuilder;
import com.testportal.testportal.commonframework.exceptions.InvalidCredentialsException;
import com.testportal.testportal.commonframework.exceptions.ResourceAlreadyExistsException;
import com.testportal.testportal.commonframework.exceptions.ResourceNotFoundException;
import com.testportal.testportal.commonframework.exceptions.ValidationException;
import com.testportal.testportal.commonframework.security.SecurityUtility;
import com.testportal.userprofileservice.dto.CredentialsDto;
import com.testportal.userprofileservice.dto.RoleDto;
import com.testportal.userprofileservice.dto.UserDto;
import com.testportal.userprofileservice.entity.UserProfile;
import com.testportal.userprofileservice.helper.UserProfileServiceHelper;
import com.testportal.userprofileservice.repository.UserProfileRepository;

@Service
public class UserProfileService {

	private Logger log = LoggerFactory.getLogger(ProfileServiceProvider.class);

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserProfileServiceHelper userProfileServiceHelper;
	@Autowired
	private SecurityUtility securityUtility;

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
	public ApiResponse createNewUser(UserDto userDto, MultipartFile image) throws ResourceAlreadyExistsException, ValidationException {

		userProfileServiceHelper.validateUserDetails(userDto, true);
		UserProfile newUser = mapper.map(userDto, UserProfile.class);
		UserProfile adddedUser = null;
		if (userDto.getRole() != null && userDto.getRole().getRoles() != null && userDto.getRole().getRoles().size() != 0) {
			/*
			 * validate whether user is getting created with role (ADMIN,SUPER ADMIN) by an SUPER ADMIN not by other roles user orElse throw exception
			 */
		} else {
			adddedUser = userProfileServiceHelper.addUserWithRoles(newUser);
		}
		adddedUser.setPassword(null);
		return ApiResponse.builder().data(mapper.map(adddedUser, UserDto.class)).build();
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

	public ApiResponse updateUser(UserDto userDto, MultipartFile image) throws ValidationException {
		userProfileServiceHelper.validateUserDetails(userDto, false);
		return null;
	}

	public UserDto getUserById(Long id) throws ResourceNotFoundException {
		UserProfile user = userProfileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User found with id : " + id));
		return mapper.map(user, UserDto.class);
	}

	public ApiResponse getUserByUsername(String userName) throws ResourceNotFoundException {
		if (userName != null) {
			Optional<UserProfile> u = userProfileRepository.findByUsername(userName);
			if (u.isPresent()) {
				UserDto user = mapper.map(u.get(), UserDto.class);
				List<String> userRoles = u.get().getRoles().stream().map(role -> role.getRole().getRole()).collect(Collectors.toList());
				user.setRole(RoleDto.builder().roles(userRoles).build());
				return ApiResponse.builder().data(user).build();
			} else {
				log.warn("user not found with username: {}", userName);
				throw new ResourceNotFoundException("No user found with username " + userName);
			}
		}
		return null;
	}

//	public UserDto getUserByCredentials(CredentialsDto credentials) throws UserNotFoundException, InvalidCrentials {
//		if (credentials != null && credentials.getUsername() != null && credentials.getPassword() != null) {
//			Optional<UserProfile> u = userProfileRepository.findByUsernameAndPassword(credentials.getUsername(), credentials.getPassword());
//			if (u.isPresent()) {
//				return mapper.map(u.get(), UserDto.class);
//			} else {
//				log.error("user not found with username: {}", credentials.getUsername());
//				throw new UserNotFoundException();
//			}
//		} else {
//			throw new InvalidCrentials("Invalid Credentials!");
//		}
//	}

	public ApiResponse getUserByCredentials(CredentialsDto credentials) throws ResourceNotFoundException, InvalidCredentialsException, ValidationException {
		ApiResponseBuilder resBuilder = ApiResponse.builder();
		if (credentials != null && credentials.getUsername() != null && credentials.getPassword() != null) {
			Optional<UserProfile> maybeUser = userProfileRepository.findByUsername(credentials.getUsername());
			if (maybeUser.isEmpty()) {
				throw new ResourceNotFoundException("No Resource found with username:  " + credentials.getUsername());
			}
			if (!securityUtility.validateCredentials(credentials.getPassword(), maybeUser.get().getPassword())) {
				throw new InvalidCredentialsException("Invalid Credentials passed!!!");
			}
			return resBuilder.data(mapper.map(maybeUser.get(), UserDto.class)).build();
		} else {
			throw new ValidationException("Missing attributes!!!");
		}
	}
}
