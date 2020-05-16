package com.example.demo.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.shared.Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
     BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	public UserDto createUser(UserDto user) {
		
		if(userRepository.findByEmail(user.getEmail())!=null) throw new RuntimeException("Record already exist"); 
		
		UserEntity userEntity =new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		String publicUserId=utils.generateUserId(30);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(publicUserId);
		
		UserEntity storedDetails= userRepository.save(userEntity);
		UserDto returnValue= new UserDto();
		BeanUtils.copyProperties(storedDetails, returnValue);
		 
		return returnValue;
	}
 
	@Override
	public UserDto getUser(String email) {
		
		UserEntity userEntity= userRepository.findByEmail(email);
		if(userEntity==null)throw new UsernameNotFoundException(email);
		UserDto returnValue= new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}
	
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserEntity userEntity= userRepository.findByEmail(email);
		
		if(userEntity==null)throw new UsernameNotFoundException(email);
		
	    return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),new ArrayList<>());
	}

	@Override
	public UserDto getUserById(String userId) {
		UserDto returnValue=new UserDto();
		
		UserEntity userEntity= userRepository.findByUserId(userId);
		if(userEntity==null)throw new UsernameNotFoundException(userId);
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user) {
		UserDto returnValue=new UserDto();
		
		UserEntity userEntity= userRepository.findByUserId(userId);
		if(userEntity==null)throw new UsernameNotFoundException(userId);
		BeanUtils.copyProperties(userEntity, returnValue);
		
		userEntity.setFirstname(user.getFirstname());
		userEntity.setLastname(user.getLastname());
		UserEntity updatesUserDetails= userRepository.save(userEntity);
		BeanUtils.copyProperties(updatesUserDetails, returnValue);
		return returnValue;
	}

	@Override
	public void deleteUser(String userId) {
		
		UserEntity userEntity= userRepository.findByUserId(userId);
		if(userEntity==null)
			throw new UsernameNotFoundException(userId);
		
		userRepository.delete(userEntity);
	}

}
