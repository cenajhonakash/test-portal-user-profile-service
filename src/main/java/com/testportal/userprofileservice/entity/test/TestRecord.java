package com.testportal.userprofileservice.entity.test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.testportal.userprofileservice.entity.Candidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_record")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TestRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long testId;
	private LocalDateTime testTakenOn;
	private Timestamp totalTimeTaken;
	private Long total_score;
	private Long score;
	private String result_code;
	@ManyToOne
	private Candidate candidate;
}
