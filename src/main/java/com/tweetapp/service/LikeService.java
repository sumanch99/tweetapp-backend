package com.tweetapp.service;

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
import com.tweetapp.repository.LikeRepository;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@Service
public class LikeService {
    @Autowired
    private LikeRepository repository;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;
    
    /*
   	 * Service Method to Like a tweet through username
   	 * */
    public TweetWithLikeComment likeTweet(String username, Long tweetId) throws TweetAppException {
    	
        if(userService.isUsernameValid(username)) {
        	throw new TweetAppException("Invalid Username");
        }
        
        if(tweetService.isTweetIdValid(tweetId)) {
        	throw new TweetAppException("Tweet not found!");
        }
        
        repository.save(LikeTweet.builder()
                        .tweetId(tweetId).username(username)
                .build());

        List<LikeTweet> likeList = getLikedTweetByTweetId(tweetId);
        
        List<User> usersList = userService.getAllUsersInList(likeList.stream().map(LikeTweet::getUsername).collect(Collectors.toList()));

        List<Comment> commentsList = commentService.getCommentsByTweetId(tweetId);

        Tweet tweet = tweetService.getTweetById(tweetId);
        
        return TweetWithLikeComment.builder()
                .id(tweet.getId())
                .userName(tweet.getUsername())
                .tweets(tweet.getTweet())
                .date(tweet.getDate())
                .likedUsers(usersList)
                .commentsList(commentsList)
                .build();
    }
    
    /*
   	 * Service Method to get all Likes on a tweet through tweetId
   	 * */	
    public List<LikeTweet> getLikedTweetByTweetId(Long tweetId) {
    	
        return repository.findByTweetId(tweetId);
        
    }
}
