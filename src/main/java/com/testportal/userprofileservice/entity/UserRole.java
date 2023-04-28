package com.testportal.userprofileservice.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_role")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long user_Role_ID;

	@ManyToOne
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	private UserProfile user;
}
