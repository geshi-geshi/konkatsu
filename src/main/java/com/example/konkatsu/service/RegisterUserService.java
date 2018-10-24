

package com.example.konkatsu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.konkatsu.domain.User;
import com.example.konkatsu.repository.RegisterUserRepository;

@Service
@Transactional
public class RegisterUserService {
	@Autowired
	RegisterUserRepository registerUserRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	//DBへ保存処理
	public User create (User user, String rawPassword){
		String encodedPassword = passwordEncoder.encode(rawPassword);
		user.setPass(encodedPassword);
		return registerUserRepository.save(user);
	}
}


