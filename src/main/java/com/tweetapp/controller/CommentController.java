package com.tweetapp.controller;

import com.tweetapp.exception.TweetAppException;
import com.tweetapp.model.Comment;
import com.tweetapp.model.utilityModel.ApiResponse;
import com.tweetapp.model.utilityModel.TweetWithLikeComment;
import com.tweetapp.service.CommentService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@RestController
@RequestMapping("/api/v1.0/tweets")
@Slf4j
public class CommentController {
	@Autowired
	private CommentService service;
	
	/*
	 * API Method to register a comment against a tweet through a username 
	 * */
	@PostMapping(("/{username}/reply/{tweetId}"))
	public ResponseEntity<ApiResponse> commentTweet(@RequestBody Comment comment, @PathVariable String username,
			@PathVariable Long tweetId) throws TweetAppException {
		
		log.info("Entered commentTweet");
		
		TweetWithLikeComment tweet = service.commentTweet(comment, username, tweetId);
		
		log.info("Commented on the tweet");
		
		return ResponseEntity.ok(ApiResponse.builder().status(200).message("Commented on the tweet").data(tweet).build());
	}
}
