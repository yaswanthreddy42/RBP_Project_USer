package com.example.demo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.Controller.AuthenticationController;
import com.example.demo.Model.User;
import com.example.demo.Service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthenticationControllerTest {
	
	@Mock
	private UserServiceImpl userService;
	
	@InjectMocks
	private AuthenticationController authC;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(authC).build();
	}
	
	List<User> userList = new ArrayList<User>();
	
	@Test
	public void addUserSuccess() throws Exception
	{
		User user = new User();
		user.setId(1);
		user.setUsername("admin");
		user.setPetname("dog");
		user.setEmail("admin@gmail.com");
		user.setPassword("admin");
		
		userList.add(user);
		when(userService.addUser(any())).thenReturn(user);
		
		assertEquals(1,userList.size());
mockMvc.perform(MockMvcRequestBuilders.post("/auth/v1/addUser").contentType(MediaType.APPLICATION_JSON)
		.content(new ObjectMapper().writeValueAsString(user))).andExpect(MockMvcResultMatchers.status().isCreated());
		
	}
	
	@Test
	public void addUserFailure() throws Exception
	{
		
		when(userService.addUser(any())).thenReturn(null);
		
		User u1 = userService.addUser(null);
		assertNull(u1);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/auth/v1/addUser").contentType(MediaType.APPLICATION_JSON)
		.content(new ObjectMapper().writeValueAsString(u1))).andExpect(MockMvcResultMatchers.status().is4xxClientError());

		
	}

}