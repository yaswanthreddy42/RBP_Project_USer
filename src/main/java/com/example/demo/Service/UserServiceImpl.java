package com.example.demo.Service;

import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService
{

	@Autowired
	private UserRepository userRepo;
	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

	@Override
	public User addUser(User user) {
		if(user!=null)
		{
			return userRepo.saveAndFlush(user);
			
		}
		return null;
	}

	@Override
	public boolean loginUser(String username, String password,String userRole) {
		
		User user1 = userRepo.validateUser(username, password,userRole);
		System.out.println("User: "+ user1.getUsername());
		if(user1!=null)
		{
			return true;
		}
		return false;
	}

	@Override
	public List<User> getAllUsers() {
	
		List<User> userList = userRepo.findAll();
		
		if(userList!=null & userList.size() >0)
		{
			return userList;
		}
		else
			return null;
	}
	
	@Override
	public User getUserById(int uid) {
		Optional<User> user = userRepo.findById(uid);
		if(user.isPresent())	
		{
			return user.get();
		}
		
		return null;
	}
	
	@Override
	public int forgotPassword(String username,String petname) {

		try {
		  User user2 = userRepo.RequestValue(username,petname);
		  return user2.getId();
		}
		catch(Exception e) {
			System.out.println(-1);
			return -1;
		}
}
	
	@KafkaListener(topics = "userlog")
	public void consumeFromTopic(String message) {
	    try {
	        System.out.println("user " +message+" logged in");
	    } catch (Exception e) {
	        System.err.println("Error processing Kafka message: " + e.getMessage());
	    }
	}
	
}