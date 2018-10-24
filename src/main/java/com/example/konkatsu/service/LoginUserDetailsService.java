//usersテーブルから取得した情報を用いて LoginUserDetailsを作るクラス


package com.example.konkatsu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.konkatsu.domain.User;
import com.example.konkatsu.repository.RegisterUserRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	 RegisterUserRepository registerUserRepository;

	@Override
	//loadUserByUsernameでは、UserDetails形式で値を返すという宣言
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		User user = registerUserRepository.findByMail(mail);
		if(user == null){
			throw new UsernameNotFoundException("ユーザーが見つかりません");
		}
		return new LoginUserDetails(user);  //UserDetails形式で値を返すという宣言
	}
	// aaaから修正


}
