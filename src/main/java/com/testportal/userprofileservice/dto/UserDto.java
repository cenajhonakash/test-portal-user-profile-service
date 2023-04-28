package com.testportal.userprofileservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

	private Long userID;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String phone;
	private String about;
	private String username;
	private boolean enabled;
	private RoleDto role;
}
