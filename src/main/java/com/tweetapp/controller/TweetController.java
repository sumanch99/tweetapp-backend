package com.tweetapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.exception.TweetAppException;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.utilityModel.ApiResponse;
import com.tweetapp.model.utilityModel.TweetWithLikeComment;
import com.tweetapp.service.TweetService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@RestController
@RequestMapping("/api/v1.0/tweets")
@Slf4j
public class TweetController {
	
	@Autowired
	private TweetService service;
	
	/*
	 * API Method to get All the tweets present in DB irrespective of users
	*/
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllTweets() {
		
		log.info("Entered getAllTweets");
		
		List<TweetWithLikeComment> tweetList = service.getAllTweets();
		
		if (!tweetList.isEmpty()) {
			
			log.info("Tweets Fetched successfully");
			
			return ResponseEntity.ok(
					ApiResponse.builder().status(200).message("Tweets Fetched successfully").data(tweetList).build());
		
		}
		
		log.info("Tweets not found");
		
		return ResponseEntity.ok()
				.body(ApiResponse.builder().status(200).message("No Tweets available").data(tweetList).build());

	}
	
	/*
	 * API Method to get All the tweets tweeted by an user through username
	*/
	@GetMapping("/{username}")
	public ResponseEntity<ApiResponse> getAllTweetsForAUser(@PathVariable String username) throws TweetAppException {
		
		log.info("Entered getAllTweetsForAUser");
		
		List<TweetWithLikeComment> tweets = service.getAllTweetsForAUser(username);
		
		if (tweets.isEmpty()) {
			
			log.info("No tweets found for user" + username);
			
			return ResponseEntity.ok(ApiResponse.builder().status(200).message("No tweets found for user " + username)
					.data(tweets).build());
			
		}
		
		log.info("Tweets Fetched successfully for user " + username);
		
		return ResponseEntity.ok(ApiResponse.builder().status(200)
				.message("Tweets Fetched successfully for user " + username).data(tweets).build());

	}
	
	/*
	 * API Method to post a new tweet by an user
	*/
	@PostMapping("/{username}/add")
	public ResponseEntity<ApiResponse> createTweet(@RequestBody Tweet tweet, @PathVariable String username)
			throws TweetAppException {
		
		log.info("Entered postTweet");
		
		Tweet createdTweet = service.createTweet(tweet, username);
		
		log.info("Tweet created successfully for user " + username);
		
		return ResponseEntity.ok(ApiResponse.builder().status(201)
				.message("Tweet created successfully for user " + username).data(createdTweet).build());

	}
	
	/*
	 * API Method to update an already posted tweet
	*/
	@PutMapping("{username}/update/{tweetId}")
	public ResponseEntity<ApiResponse> updateTweet(@RequestBody Tweet tweet, @PathVariable String username,
			@PathVariable Long tweetId) throws TweetAppException {
		
		log.info("Entered updateTweet");
		
		Tweet tweetCreated = service.updateTweet(username, tweetId, tweet);
		
		log.info("Tweets Updated successfully for user " + username);
		
		return ResponseEntity.ok(ApiResponse.builder().status(201)
				.message("Tweets Updated successfully for user " + username).data(tweetCreated).build());

	}
	
	/*
	 * API Method to delete an already posted tweet
	*/
	@DeleteMapping("{username}/delete/{tweetId}")
	public ResponseEntity<ApiResponse> deleteTweet(@PathVariable String username, @PathVariable Long tweetId) throws TweetAppException {
		
		log.info("Entered deleteTweet");
		
		service.deleteTweet(username, tweetId);
		
		log.info("Tweet deleted successfully");
		
		return ResponseEntity.ok(ApiResponse.builder().status(200).message("Tweet deleted successfully").build());

	}

}
