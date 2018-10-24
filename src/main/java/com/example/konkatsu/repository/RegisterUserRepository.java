package com.example.konkatsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.konkatsu.domain.User;


//JpaRepositoryを継承して対象となるモデルをimportしておけばfineOne、findAll、save、deleteを自動で使えるようにな
public interface RegisterUserRepository extends JpaRepository<User, String>{
	User findByMail(String mail);

	// マスターからの修正
}
