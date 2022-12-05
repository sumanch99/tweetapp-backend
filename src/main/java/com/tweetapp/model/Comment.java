package com.tweetapp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(nullable = false)
	private String comment;
	
	@Column(nullable = false)
	private long tweetId;
	
	@Column(nullable = false)
	private Date date;
	
	@Column(nullable = false)
	private String username;

}
