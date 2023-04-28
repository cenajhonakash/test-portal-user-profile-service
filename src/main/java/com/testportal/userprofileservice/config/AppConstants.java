package com.testportal.userprofileservice.config;

public enum AppConstants {

	NORMAL("1"), CANDIDATE("2"), ADMIN("3"), SUPER_ADMIN("4");

	private Long roleId;

	public Long id() {
		return this.roleId;
	}

	private AppConstants(String id) {
		this.roleId = Long.parseLong(id);
	}
}
