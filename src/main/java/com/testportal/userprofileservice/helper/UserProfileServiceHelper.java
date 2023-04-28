package com.testportal.userprofileservice.helper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.testportal.userprofileservice.config.AppConstants;
import com.testportal.userprofileservice.dto.UserDto;
import com.testportal.userprofileservice.entity.Role;
import com.testportal.userprofileservice.entity.UserProfile;
import com.testportal.userprofileservice.entity.UserRole;
import com.testportal.userprofileservice.exception.MissingMandatoryAttribute;
import com.testportal.userprofileservice.exception.ResourceAlreadyPresent;
import com.testportal.userprofileservice.repository.RoleRepository;
import com.testportal.userprofileservice.repository.UserProfileRepository;
import com.testportal.userprofileservice.repository.UserRoleRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserProfileServiceHelper {

	private final BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Value("${user.role.default}")
	private String[] defaultUserRoles;

	public void validateUserDetails(UserDto userDto, boolean isNew) throws MissingMandatoryAttribute {
		if (!isNew) {
			return;
		}
		if (userDto.getFirstName() == null || userDto.getFirstName().trim().length() == 0 || userDto.getLastName() == null || userDto.getLastName().trim().length() == 0) {
			log.warn("mandatory attribute is missing from payload: {}", userDto);
			throw new MissingMandatoryAttribute("mandatory attribute is missing from payload: " + userDto);
		}
		if (userDto.getFirstName() == null || userDto.getFirstName().trim().length() == 0 || userDto.getLastName() == null || userDto.getLastName().trim().length() == 0) {
			log.warn("mandatory attribute is missing from payload: {}", userDto);
			throw new MissingMandatoryAttribute("mandatory attribute is missing from payload: " + userDto);
		}
		if (userDto.getFirstName() == null || userDto.getFirstName().trim().length() == 0 || userDto.getLastName() == null || userDto.getLastName().trim().length() == 0) {
			log.warn("mandatory attribute is missing from payload: {}", userDto);
			throw new MissingMandatoryAttribute("mandatory attribute is missing from payload: " + userDto);
		}
		if (userDto.getFirstName() == null || userDto.getFirstName().trim().length() == 0 || userDto.getLastName() == null || userDto.getLastName().trim().length() == 0) {
			log.warn("mandatory attribute is missing from payload: {}", userDto);
			throw new MissingMandatoryAttribute("mandatory attribute is missing from payload: " + userDto);
		}

	}

	public void encodeDecodePassword(UserProfile user, boolean isEncode) {
		if (isEncode) {
			user.setPassword(passEncoder.encode(user.getPassword()));
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
	public UserProfile addUserWithRoles(UserProfile newUser) throws ResourceAlreadyPresent {

		validateUserWithUserName(newUser);

		addRoles(Arrays.asList(defaultUserRoles), newUser);

		newUser.setEnabled(true);
		encodeDecodePassword(newUser, true);

		UserProfile user = userProfileRepository.saveAndFlush(newUser);

		return user;
	}

	private void validateUserWithUserName(UserProfile newUser) throws ResourceAlreadyPresent {
		Optional<UserProfile> maybeUser = userProfileRepository.findByUsername(newUser.getUsername());
		if (maybeUser.isPresent()) {
			log.warn("User alreday exists with username: {}", newUser.getUsername());
			throw new ResourceAlreadyPresent("User alreday exists with username: " + newUser.getUsername());
		}
	}

	private void addRoles(List<String> userRoles, UserProfile newUser) {

		Set<UserRole> roleSet = new HashSet<>();
		List<Role> roleList = userRoles.stream().map(roleName -> Role.builder().role(roleName).roleId(findRoleIdForRole(roleName)).build()).collect(Collectors.toList());
//		Role r = new Role();
//		r.setRole("NORMAL");
//		r.setRoleId(2L);

		roleSet = roleList.stream().map(role -> UserRole.builder().role(role).user(newUser).build()).collect(Collectors.toSet());
//		UserRole ur = new UserRole();
//		ur.setUser(newUser);
//		ur.setRole(r);

		// roleSet.add(ur);
		roleSet.forEach(userRole -> {
			roleRepository.save(userRole.getRole());
		});
		newUser.getRoles().addAll(roleSet);)
	}

	private Long findRoleIdForRole(String roleName) {

		if (roleName.contains(AppConstants.NORMAL.name())) {
			return AppConstants.NORMAL.id();
		}
		if (roleName.contains(AppConstants.CANDIDATE.name())) {
			return AppConstants.CANDIDATE.id();
		}
		if (roleName.contains(AppConstants.ADMIN.name())) {
			return AppConstants.ADMIN.id();
		}
		if (roleName.contains(AppConstants.SUPER_ADMIN.name())) {
			return AppConstants.SUPER_ADMIN.id();
		}
		return null;
	}

}
