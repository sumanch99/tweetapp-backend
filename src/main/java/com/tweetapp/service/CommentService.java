package com.tweetapp.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.tweetapp.exception.TweetAppException;
import com.tweetapp.model.Comment;
import com.tweetapp.model.LikeTweet;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import com.tweetapp.model.utilityModel.TweetWithLikeComment;
import com.tweetapp.repository.CommentRepository;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@Service
public class CommentService {
	
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private LikeService likeService;
    
    /*
	 * Service Method to comment on a tweet through username
	 * */	
    public TweetWithLikeComment commentTweet(Comment comments, String username, Long tweetId) throws TweetAppException {
        
    	if(userService.isUsernameValid(username)) {
        	throw new TweetAppException("Username doesn't exists");
        }
            
        if(tweetService.isTweetIdValid(tweetId)) {
        	throw new TweetAppException("Tweet id doesn't exists");
        }
            
        comments.setUsername(username);
        
        comments.setTweetId(tweetId);
        
        comments.setDate(new Date());
        
        commentRepository.saveAndFlush(comments);
        
        Tweet tweet = tweetService.getTweetById(tweetId);
        
        List<LikeTweet> likeList = likeService.getLikedTweetByTweetId(tweetId);
        
        List<User> userList = userService.getAllUsersInList(likeList.stream().map(LikeTweet::getUsername).collect(Collectors.toList()));
        
        List<Comment> commentList = getCommentsByTweetId(tweetId);
        return TweetWithLikeComment.builder()
                .id(tweet.getId())
                .userName(tweet.getUsername())
                .tweets(tweet.getTweet())
                .date(tweet.getDate())
                .likedUsers(userList)
                .commentsList(commentList)
                .build();
    }
    
    /*
	 * Service Method to get all comments on a tweet through tweetId
	 * */	
    public List<Comment> getCommentsByTweetId(Long tweetId) {
    	
        return commentRepository.findByTweetId(tweetId);
        
    }
}
