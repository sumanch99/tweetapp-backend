package com.tweetapp.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@Data
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(nullable = false)
	private String email;
	
	@Column(unique = true,nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String firstName;
	
	private String lastName;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String contactNumber;
	
	@Column(nullable = false)
	private Date dob;
	
	@Column(nullable = false)
	private String gender;
	
}
