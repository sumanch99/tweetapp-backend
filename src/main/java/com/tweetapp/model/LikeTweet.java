package com.tweetapp.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeTweet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(nullable = true)
	private String username;
	
	@Column(nullable = true)
	private long tweetId;

}
