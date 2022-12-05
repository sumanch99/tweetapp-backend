package com.tweetapp.model.utilityModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/*
 * Class to send Custom API response for tweet app
 */
public class ApiResponse {
	
    private int status;
    
    private String message;
    
    private Object data;
}
