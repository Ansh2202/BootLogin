package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.dto.UserDto;

public interface UserService extends UserDetailsService{
UserDto createUser(UserDto user);
UserDto getUser(String email);
UserDto getUserById(String userId);
UserDto updateUser(String userId,UserDto user);
void deleteUser(String userId);
}
