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

import com.testportal.userprofileservice.dto.CandidateDto;
import com.testportal.userprofileservice.dto.CredentialsDto;
import com.testportal.userprofileservice.dto.UserDto;
import com.testportal.userprofileservice.exception.InvalidCrentials;
import com.testportal.userprofileservice.exception.UserNotFoundException;
import com.testportal.userprofileservice.service.UserProfileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/internal/profile")
@Slf4j
public class UserController {

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
	public ResponseEntity<UserDto> createUser(@RequestBody(required = true) UserDto userDto) {
		return new ResponseEntity<UserDto>(profileService.createNewUser(userDto, null), HttpStatus.CREATED);
	}

	@GetMapping("/{userName}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userName") String userName) {
		try {
			return new ResponseEntity<UserDto>(profileService.getUserByUsername(userName), HttpStatus.FOUND);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<UserDto> getUserByCredentials(@RequestBody(required = true) CredentialsDto credentials) {
		try {
			return new ResponseEntity<UserDto>(profileService.getUserByCredentials(credentials), HttpStatus.FOUND);
		} catch (UserNotFoundException e) {
			log.error(e.getMessage());
		} catch (InvalidCrentials e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@DeleteMapping("/{userID}")
	public void deleteUser(@PathVariable("userID") Long userID) {

	}

	@PutMapping("/update/{userID}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userID") Long userID) {
		return new ResponseEntity<UserDto>(profileService.updateUser(userDto, null), HttpStatus.OK);
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
