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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
	
    private String oldPassword;
    
    private String newPassword;
}
