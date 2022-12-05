package com.tweetapp.repository;

import com.tweetapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
	
    List<Comment> findByTweetId(Long tweetId);
    
    Optional<Comment> findByUsername(String username);
    
}
