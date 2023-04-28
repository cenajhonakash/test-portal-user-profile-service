package com.testportal.userprofileservice.entity;

import java.util.List;

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
import com.testportal.userprofileservice.entity.test.TestRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "candidate")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long candidateId;

	private String firstName;

	@Column(unique = true, updatable = false, nullable = false)

	private Long userId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "candidate")
	@JsonIgnore
	private List<TestRecord> testRecords;
}
