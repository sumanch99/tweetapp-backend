package com.tweetapp.repository;

import com.tweetapp.model.LikeTweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@Repository
public interface LikeRepository extends JpaRepository<LikeTweet, Long> {
	
    List<LikeTweet> findByTweetId(Long tweetId);
    
}
