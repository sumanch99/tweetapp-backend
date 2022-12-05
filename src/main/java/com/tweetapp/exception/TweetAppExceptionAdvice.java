package com.tweetapp.exception;

import com.tweetapp.model.utilityModel.ApiResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@RestControllerAdvice
@Slf4j
public class TweetAppExceptionAdvice {
	
	public static final String GLOBAL_EXCEPTION_MESSAGE = "An Unknown error ocurred! Please contact helpdesk of tweet app.";
	
	public static final String USERNAME_NOTFOUND_EXCEPTION = "UsernameNotFoundException";
	
    @ExceptionHandler(value = {TweetAppException.class})
    public ResponseEntity<ApiResponse> exception(TweetAppException exception) {
    	exception.printStackTrace();
    	log.warn(exception.getMessage());
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                        .status(403).message(exception.getMessage())
                .build());
    }
    
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiResponse> exception(Exception exception) {
    	exception.printStackTrace();
    	log.error(exception.getMessage());
        return ResponseEntity.internalServerError().body(ApiResponse.builder()
                        .status(500).message(GLOBAL_EXCEPTION_MESSAGE)
                .build());
    }
    
    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<ApiResponse> exception(UsernameNotFoundException exception) {
    	exception.printStackTrace();
    	log.error(exception.getMessage());
        return ResponseEntity.internalServerError().body(ApiResponse.builder()
                        .status(500).message(USERNAME_NOTFOUND_EXCEPTION)
                .build());
    }
}