package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.User;

public interface UserService 
{
	public User addUser(User user);// user registration
	
	public boolean loginUser(String username, String password,String userRole);// login
	public User getUserById(int uid);
	public int forgotPassword(String username,String petname);
	public List<User> getAllUsers();// will be visible only if you are logged in
	public void consumeFromTopic(String message);
	

}