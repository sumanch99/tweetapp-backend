package com.tweetapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tweetapp.exception.TweetAppException;
import com.tweetapp.model.User;
import com.tweetapp.model.utilityModel.ChangePassword;
import com.tweetapp.model.utilityModel.LoginModel;
import com.tweetapp.model.utilityModel.MyUserDetails;
import com.tweetapp.repository.UsersRepository;
import com.tweetapp.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@Service
@Slf4j
@Component("userDetailsImpl")
public class UserService implements UserDetailsService {
	
    @Autowired
    private UsersRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /*
   	 * Service Method to create a new user for tweet app
   	 * */
    public User createUser(User users) throws TweetAppException {
    	
    	log.info("Entered createUser");
    	
        if(users == null){
        	
        	log.error("User passed is null");
        	
        	throw new TweetAppException("User passed is null");
        	
        }
        if(repository.findByEmail(users.getEmail()).isPresent()){
        	
        	log.error("Email already exists");
        	
            throw new TweetAppException("Email already exists");
            
        }
        if(repository.findByUsername(users.getUsername()).isPresent()){
        	
        	log.error("Username already exists. Try with a different username!");
        	
            throw new TweetAppException("Username already exists. Try with a different username!");
        
        }
        
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        
        log.info("User is getting created");
        
        try {
        	
        	return repository.saveAndFlush(users);
        	
        }catch(DataIntegrityViolationException e) {
        	
        	log.error("Insufficient input details provided",e);
        	
        	throw new TweetAppException("Insufficient input details provided");
        	
        }
        
    }
    
    /*
   	 * Service Method to login an user into tweetapp 
   	 * */
    public Map<String, String> login(LoginModel user) throws TweetAppException {
    	
    	log.info("Entered login");
    	
    	Optional<User> users = repository.findByUsername(user.getUserId());
    	
    	if(users.isEmpty()) {
    		
    		users = repository.findByEmail(user.getUserId());
    		
        }
        if(users.isEmpty()) {
        	
        	throw new TweetAppException("Email address or Username not present");
        	
        }
        
        log.info("User Found to login");
        
        User u = users.get();
        
        log.info(u.toString());
        
        boolean match = passwordEncoder.matches(user.getPassword(), u.getPassword());
        
        if(match) {
        	
        	UserDetails userDetails = loadUserByUsername(u.getUsername());
        	
        	String jwt = jwtUtil.generateToken(userDetails);
        	
        	Map<String, String> response = new HashMap<>();
        	
        	response.put("username", userDetails.getUsername());
        	
        	response.put("jwt", jwt);
        	
        	log.info("Login Successful, user"+response.get("username")+" ,jwt:"+response.get("jwt"));
        	
        	return response;
        }else {
        	
        	log.error("Incorrect Credentials");
        	
        	throw new TweetAppException("Incorrect Credentials");
        	
        }
        

    }
    
    /*
   	 * Service Method to update password of an user
   	 * */
    public User updatePassword(ChangePassword cp,String username) throws TweetAppException {
    	
    	log.info("Entered updatePassword");
    	
    	Optional<User> user = repository.findByUsername(username);
    	
        if(user.isEmpty()) {
        	
        	log.error("Username not found");
        	
        	throw new TweetAppException("Username not found");
        	
        }
        
        User users = user.get();
        
        if(!passwordEncoder.matches(cp.getOldPassword(), users.getPassword())) {
        	throw new TweetAppException("Old password doesnot match");
        }

        users.setPassword(passwordEncoder.encode(cp.getNewPassword()));
        
        log.info("Password Changed Successfully");
        
        return repository.saveAndFlush(users);
    }
    
    /*
   	 * Service Method to check whether an username is valid and present in DB
   	 * */
    public boolean isUsernameValid(String username){
    	
        return repository.findByUsername(username).isEmpty();
        
    }
    
    /*
   	 * Service Method to get All users present in DB
   	 * */
    public List<User> getAllUsers(){
    	
        return repository.findAll();
        
    }
    
    /*
   	 * Service Method to get All users with an username present in DB through Regex based character search
   	 * */
    public List<User> getUserByRegex(String username) {
    	
        return repository.findByUsernameContains(username);
        
    }
    
    /*
   	 * Service Method to get user present in DB through email
   	 * */
    public User getUserByEmail(String email) throws TweetAppException {
    	
        if(isUsernameValid(email)) {
        	throw new TweetAppException("Username Invalid");
        }
        
        return repository.findByEmail(email).get();
    }
    
    /*
   	 * Service Method to get List of Users through a list of usernames
   	 * */
    public List<User> getAllUsersInList(List<String> usernames){
    	
        return repository.findByUsernameIn(usernames);
        
    }

    /*
   	 * Service Method to load an User By userId i.e. either by email or by username
   	 * */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    	
    	log.info("Enterted loadUserByUsername");
    	
        Optional<User> user = repository.findByEmail(userId);

        if(user.isEmpty()) {
        	
        	user = repository.findByUsername(userId);
        	
        }
        
        if(user.isEmpty()) {
        	
        	throw new UsernameNotFoundException("Email or Username not present");
        	
        }
        
        return user.map(MyUserDetails::new).get();
    }
    
}
