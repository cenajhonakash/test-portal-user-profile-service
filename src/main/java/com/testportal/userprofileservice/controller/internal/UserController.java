package com.testportal.userprofileservice.controller.internal;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testportal.testportal.commonframework.controller.BaseRestController;
import com.testportal.testportal.commonframework.dto.ApiResponse;
import com.testportal.testportal.commonframework.exceptions.InvalidCredentialsException;
import com.testportal.testportal.commonframework.exceptions.ResourceAlreadyExistsException;
import com.testportal.testportal.commonframework.exceptions.ResourceNotFoundException;
import com.testportal.testportal.commonframework.exceptions.ValidationException;
import com.testportal.userprofileservice.dto.CandidateDto;
import com.testportal.userprofileservice.dto.CredentialsDto;
import com.testportal.userprofileservice.dto.UserDto;
import com.testportal.userprofileservice.service.UserProfileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/internal/profile")
@Slf4j
public class UserController extends BaseRestController {

	@Autowired
	public UserProfileService profileService;

	/*
	 * API used by roles[NORMAL, ADMIN, SUPER ADMIN]
	 */
//	@PostMapping("/register")
//	public ResponseEntity<UserDto> createUser(@RequestBody(required = true) UserDto userDto, @RequestPart("imageURL") MultipartFile imageURL) {
//		return new ResponseEntity<UserDto>(profileServiceProvider.createNewUser(userDto, imageURL), HttpStatus.CREATED);
//	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> createUser(@RequestBody(required = true) UserDto userDto) throws ResourceAlreadyExistsException, ValidationException {
		return new ResponseEntity<>(profileService.createNewUser(userDto, null), HttpStatus.CREATED);
	}

	/*
	 * JWT protected endpoint
	 */
	@GetMapping("/{userName}")
	public ResponseEntity<ApiResponse> getUser(@PathVariable("userName") String userName) throws ResourceNotFoundException {
		return new ResponseEntity<>(profileService.getUserByUsername(userName), HttpStatus.FOUND);
	}

	@PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> getUserByCredentials(@RequestBody(required = true) CredentialsDto credentials)
			throws ResourceNotFoundException, InvalidCredentialsException, ValidationException {
		return new ResponseEntity<>(profileService.getUserByCredentials(credentials), HttpStatus.FOUND);
	}

	/*
	 * JWT protected endpoint
	 */
	@DeleteMapping("/{userID}")
	public void deleteUser(@PathVariable("userID") Long userID) {

	}

	/*
	 * JWT protected
	 */
	@PutMapping("/update/{userID}")
	public ResponseEntity<ApiResponse> updateUser(@RequestBody UserDto userDto, @PathVariable("userID") Long userID) throws ValidationException {
		return new ResponseEntity<>(profileService.updateUser(userDto, null), HttpStatus.OK);
	}

	/*
	 * API for ADMIN & SUPER ADMIN
	 */
	@PutMapping("/update-Candidate/{userID}")
	public ResponseEntity<UserDto> updateCandidate(@PathVariable("userID") Long candidateId) {
		return null;
	}

	/*
	 * API for ADMIN & SUPER ADMIN
	 */
	@GetMapping("/fetch/candidates")
	public ResponseEntity<List<CandidateDto>> getCandidates() {
		return null;
	}

	/*
	 * API for ADMIN & SUPER ADMIN
	 */
	@GetMapping("/fetch/candidate")
	public ResponseEntity<CandidateDto> getCandidateDetails(@RequestParam("candidateId") Long candidateId) {
		return null;
	}
}
