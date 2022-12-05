package com.tweetapp.model.utilityModel;

import lombok.Data;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@Data
public class LoginModel {
	
    private String userId;
    
    private String password;
    
    private String jwt;
}
