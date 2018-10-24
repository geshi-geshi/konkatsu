//UserDetailsの実装クラス

package com.example.konkatsu.service;

import org.springframework.security.core.authority.AuthorityUtils;

import com.example.konkatsu.domain.User;

import lombok.Data;

@Data
public class LoginUserDetails extends org.springframework.security.core.userdetails.User{
	private final User user;


	public LoginUserDetails(User user){
		super(user.getMail(), user.getPass(), AuthorityUtils.createAuthorityList("ROLE_USER"));
		this.user = user;
	}
	// aaaaaaa

	
}
