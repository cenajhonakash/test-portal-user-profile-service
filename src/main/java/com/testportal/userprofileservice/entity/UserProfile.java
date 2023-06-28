package com.testportal.userprofileservice.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long userID;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String phone;
	private String imageURL;
	private String about;
	@Column(unique = true, nullable = false)
	private String username;
	private boolean enabled;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	@JsonIgnore
	private Set<UserRole> roles;
}
