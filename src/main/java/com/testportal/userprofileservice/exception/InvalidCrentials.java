package com.testportal.userprofileservice.exception;

public class InvalidCrentials extends Exception {

	public InvalidCrentials() {
		super("Invalid Credentials");
	}

	public InvalidCrentials(String message) {
		super(message);
	}

}
