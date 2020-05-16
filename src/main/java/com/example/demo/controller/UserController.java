package com.example.demo.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.model.request.UserDetailsRequest;
import com.example.demo.model.response.OperationStatusModel;
import com.example.demo.model.response.RequestOperationName;
import com.example.demo.model.response.RequestOperationStatus;
import com.example.demo.model.response.UserRest;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping(path="/{id}")
	public UserRest getUsers(@PathVariable String id)
	{
		UserRest returnValue=new UserRest();
		UserDto userDto=userService.getUserById(id);
		BeanUtils.copyProperties(userDto,returnValue );
		return returnValue;
	}
	
	@PostMapping
	public UserRest createUser(@RequestBody UserDetailsRequest userDetails)
	{
		UserRest returnValue=new UserRest();
		UserDto userDto=new UserDto();
		BeanUtils.copyProperties(userDetails,userDto);
		UserDto createdUser= userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser,returnValue);
		return returnValue;
	}
	
	@PutMapping(path="/{id}")
	public UserRest updUsers(@PathVariable String id,@RequestBody UserDetailsRequest userDetails)
	{
	
		UserRest returnValue=new UserRest();
		UserDto userDto=new UserDto();
		BeanUtils.copyProperties(userDetails,userDto);
		UserDto updateUser= userService.updateUser(id,userDto);
		BeanUtils.copyProperties(updateUser,returnValue);
		return returnValue;
		
	}
	@DeleteMapping(path="/{id}")
	public OperationStatusModel delUsers(@PathVariable String id)
	{
		OperationStatusModel returnValue=new OperationStatusModel();
		
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		userService.deleteUser(id);
		
		returnValue.setOpeartionResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
}
