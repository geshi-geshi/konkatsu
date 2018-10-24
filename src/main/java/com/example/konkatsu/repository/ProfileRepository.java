package com.example.konkatsu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.konkatsu.domain.Profile;

@Repository
	public interface ProfileRepository extends JpaRepository<Profile, Integer> {
		//JPQLとはDBのレコードに対応するEntityを操作(SELECT/UPDATE/DELETE)するためのQuery言語
		//@QueryをつけてJPQLを記述
	    @Query("SELECT x FROM Profile x ORDER BY x.genderId, x.name, x.birthday, x.height, x.occupationId, x.income, x.text, x.image")
	    List<Profile> findAllUsers();

	    @Query("SELECT x FROM Profile x ORDER BY x.genderId, x.name, x.birthday, x.height, x.occupationId, x.income, x.text, x.image")
	    Page<Profile> findAllUsers(Pageable pageable);

	    //ログインユーザーの異性を表示
	    @Query("SELECT x FROM Profile x where x.genderId != ?1 ORDER BY x.id DESC")
	    Page<Profile> findUsersByGenderId(Integer genderId, Pageable pageable);

	    //ログインユーザーのプロフィールを表示
	    @Query("SELECT x FROM Profile x where x.id = ?1")
	    Profile findProfileByUserId(Integer id);

	    //キーワード検索結果プロフィールを表示
	    @Query("SELECT x FROM Profile x where x.name || x.birthday || x.occupation.occupationName|| x.height || x.income|| x.text LIKE %:searchName% AND x.genderId != :genderId ORDER BY x.id DESC")
	    Page<Profile> findProfilesBySearchName(@Param(value = "searchName") String searchName,  @Param(value = "genderId") Integer genderId, Pageable pageable);

	    //ログインユーザーのお気に入り一覧を表示
	    @Query("SELECT x FROM Profile AS x INNER JOIN x.favorite f where f.userId = ?1")
	   	Page<Profile> findFavoriteByUserId(Integer userId, Pageable pageable);

	}
