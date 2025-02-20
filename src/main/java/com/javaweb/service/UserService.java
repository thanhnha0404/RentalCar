package com.javaweb.service;

import java.util.Optional;

import com.javaweb.beans.CustomerDTO;


public interface UserService {
	
//	public void registerUser(String email, String password);
//	public boolean updateStatusByEmail(String email);
//	Optional<UserEntity> findByEmail(String email);
	
	public CustomerDTO Login (String email, String password);
	public CustomerDTO Forgot (String email);
	public boolean ResetPass (String pass, String email);
}
