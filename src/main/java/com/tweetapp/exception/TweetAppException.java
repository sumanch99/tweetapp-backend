package com.tweetapp.exception;

/**
 * @author Suman Chakraborty
 * December 2022
 */
public class TweetAppException extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3389796627510607484L;
	public TweetAppException(String message) {
        super(message);
    }
    public TweetAppException(String message, Throwable throwable){
        super(message,throwable);
    }
}
