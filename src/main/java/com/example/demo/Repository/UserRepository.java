package com.example.demo.Repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer>
{
	
	@Query(value="select u from User u where u.username= :username and u.password= :password and u.userRole=:userRole ")
	public User validateUser(String username, String password,String userRole);//login
    
	@Query(value="select u from User u where u.username= :username and u.petname= :petname")
	public User RequestValue(String username,String petname);
	
	@Query(value="select id from User u where u.username= :username")
	public User validate(String username);
	
	User findByUsername(String username);

}