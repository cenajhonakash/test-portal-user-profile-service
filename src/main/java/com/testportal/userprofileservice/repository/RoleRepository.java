package com.testportal.userprofileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testportal.userprofileservice.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
