package com.testportal.userprofileservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testportal.userprofileservice.dto.CandidateDto;
import com.testportal.userprofileservice.dto.UserDto;
import com.testportal.userprofileservice.service.AdminServiceProvider;

@RestController
@RequestMapping("/v1/admin")
public class AdminController {

	@Autowired
	private AdminServiceProvider adminServiceProvider;

	@PutMapping("/update-Candidate/{userID}")
	public ResponseEntity<UserDto> updateCandidate(@PathVariable("userID") Long candidateId) {
		return null;
	}

	/**
	 * controller for superAdmin role user, it'll fetch all candidate irrespective of any authorized assignment of candidate to admin
	 * 
	 * @return
	 */
	@GetMapping("/fetch/candidates")
	public ResponseEntity<List<CandidateDto>> getCandidates() {
		return null;
	}

	/*
	 * fetch candidate details
	 */
	@GetMapping("/fetch/candidate")
	public ResponseEntity<CandidateDto> getCandidateDetails(@RequestParam("candidateId") Long candidateId) {
		return null;
	}
}
