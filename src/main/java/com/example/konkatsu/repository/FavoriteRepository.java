package com.example.konkatsu.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.konkatsu.domain.Favorite;
import com.example.konkatsu.domain.FavoriteKeyId;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteKeyId>{

	//お気に入りに追加
	@Modifying //メソッドがUPDATE文であることを明示
	@Transactional //トランザクションの開始、コミット、ロールバックが自動で行われる
	@Query(value = "INSERT INTO FAVORITE (USER_ID,FAVORITE_USER_ID) VALUES (?1, ?2)", nativeQuery = true)
	void createFavorite(Integer userId, Integer favoriteUserId);

	//お気に入りを解除
	@Modifying
    @Transactional
	@Query(value = "DELETE FROM FAVORITE WHERE USER_ID = ?1 AND FAVORITE_USER_ID = ?2", nativeQuery = true)
	void deleteFavorite(Integer userId, Integer favoriteUserId);
}
