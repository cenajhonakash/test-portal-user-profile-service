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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_role")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long user_Role_ID;

	@ManyToOne
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	private UserProfile user;
}
