package com.testportal.userprofileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testportal.userprofileservice.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
