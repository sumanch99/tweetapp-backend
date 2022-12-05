package com.tweetapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.exception.TweetAppException;
import com.tweetapp.model.Comment;
import com.tweetapp.model.LikeTweet;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import com.tweetapp.model.utilityModel.TweetWithLikeComment;
import com.tweetapp.repository.CommentRepository;
import com.tweetapp.repository.LikeRepository;
import com.tweetapp.repository.TweetRepository;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@Service
public class TweetService {
    @Autowired
    private TweetRepository tweetRepository;
    
    @Autowired
    private LikeRepository likeRepository;
    
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;
    
    /*
   	 * Service Method to get all tweets present in DB
   	 * */
    public List<TweetWithLikeComment> getAllTweets(){
    	
    	List<TweetWithLikeComment> result = new ArrayList<>();
    	
    	List<Tweet> allTweets = tweetRepository.findAll();
    	
    	for(Tweet tweet:allTweets) {
    		
    		List<LikeTweet> likes = likeRepository.findByTweetId(tweet.getId());
    		
    		List<User> userList = userService.getAllUsersInList(likes.stream().map(LikeTweet::getUsername).collect(Collectors.toList()));
    		
    		List<Comment> comments = commentRepository.findByTweetId(tweet.getId());
    		
    		result.add(TweetWithLikeComment.builder()
    	            .id(tweet.getId())
    	            .userName(tweet.getUsername())
    	            .tweets(tweet.getTweet())
    	            .date(tweet.getDate())
    	            .likedUsers(userList)
    	            .commentsList(comments)
    	            .build());
    		
    	}
    	
        return result;
    }
    
    /*
   	 * Service Method to create a new tweet through username
   	 * */
    public Tweet createTweet(Tweet tweet, String username) throws TweetAppException {
    	
        if(userService.isUsernameValid(username)) {
        	throw new TweetAppException("Invalid username");
        }
            
        tweet.setUsername(username);
        
        tweet.setDate(new Date());
        
        return tweetRepository.saveAndFlush(tweet);
    }
    
    /*
   	 * Service Method to get all tweets tweeted by an user through username
   	 * */
    public List<TweetWithLikeComment> getAllTweetsForAUser(String username) throws TweetAppException {
        
    	if(userService.isUsernameValid(username)) {
        	throw new TweetAppException("Invalid username");
        }
    	
        List<TweetWithLikeComment> result = new ArrayList<>();
        
        List<Tweet> allTweets = tweetRepository.findByUsername(username);
        
        for(Tweet tweet:allTweets) {
        	
    		List<LikeTweet> likes = likeRepository.findByTweetId(tweet.getId());
    		
    		List<User> userList = userService.getAllUsersInList(likes.stream().map(LikeTweet::getUsername).collect(Collectors.toList()));
    		
    		List<Comment> comments = commentRepository.findByTweetId(tweet.getId());
    		
    		result.add(TweetWithLikeComment.builder()
    	            .id(tweet.getId())
    	            .userName(tweet.getUsername())
    	            .tweets(tweet.getTweet())
    	            .date(tweet.getDate())
    	            .likedUsers(userList)
    	            .commentsList(comments)
    	            .build());
    	}
        
        return result;
    }
    
    /*
   	 * Service Method to check if a tweet is present in DB through tweetId 
   	 * */
    public boolean isTweetIdValid(Long tweetId){
    	
        return tweetRepository.findById(tweetId).isEmpty();
        
    }
    
    /*
   	 * Service Method to update a tweet
   	 * */
    public Tweet updateTweet(String username, Long tweetId, Tweet tweet) throws TweetAppException {
        
    	if(isTweetIdValid(tweetId)) {
        	throw new TweetAppException("Tweet Not present");
        }
    	
        if(userService.isUsernameValid(username)) {
        	throw new TweetAppException("Invalid Username");
        }
        
        Tweet tweetInDb  = tweetRepository.findById(tweetId).get();
        
        tweetInDb.setTweet(tweet.getTweet());
        
        return tweetRepository.saveAndFlush(tweetInDb);
    }
    
    /*
   	 * Service Method to delete a existing tweet
   	 * */
    public void deleteTweet(String username, Long tweetId) throws TweetAppException {
    	
        if(isTweetIdValid(tweetId)) {
        	throw new TweetAppException("Tweet Not present!");
        }
        
        if(userService.isUsernameValid(username)) {
        	throw new TweetAppException("Invalid Username");
        }
        
        tweetRepository.deleteById(tweetId);
    }
    
    /*
   	 * Service Method to find a tweet from DB through tweetId
   	 * */
    public Tweet getTweetById(Long tweetId) throws TweetAppException {
    	
        if(isTweetIdValid(tweetId)) {
        	throw new TweetAppException("Invalid TweetId");
        }
            
        return tweetRepository.findById(tweetId).get();
    }
}
