package com.tweetapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.exception.TweetAppException;
import com.tweetapp.model.utilityModel.ApiResponse;
import com.tweetapp.model.utilityModel.TweetWithLikeComment;
import com.tweetapp.service.LikeService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@RestController
@RequestMapping("/api/v1.0/tweets")
@Slf4j
public class LikeController {
	@Autowired
	private LikeService service;

	/*
	 * API Method to register a like against a tweet through a username
	 * */	
	@PutMapping("/{username}/like/{tweetId}")
	public ResponseEntity<ApiResponse> likeTweet(@PathVariable String username, @PathVariable Long tweetId) throws TweetAppException {
		
		log.info("Entered likeTweet");
		
		TweetWithLikeComment tweet = service.likeTweet(username, tweetId);
		
		log.info("Liked the tweet");
		
		return ResponseEntity.ok(ApiResponse.builder().status(200).message("Liked the tweet")
				.data(tweet).build());

	}
}
