package com.example.konkatsu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.konkatsu.domain.Profile;
import com.example.konkatsu.domain.User;
import com.example.konkatsu.repository.ProfileRepository;

@Service
@Transactional
public class ProfileService {
	@Autowired
	ProfileRepository profileRepository;

	// JpaRepositoryにはfindOne,findAll,save,deleteといった(Create,Read,Update, Detele)操作の為の基本的なメソッドが定義されている

    public Profile create(Profile profile, User user) {
    	profile.setUser(user);  //Profileを登録する際に、登録者がログインユーザーのUserになるようにする
        return profileRepository.save(profile);
    }

    public Profile update(Profile profile, User user) {
    	profile.setUser(user);
        return profileRepository.save(profile);
    }

    public void delete(Integer userId) {
    	profileRepository.delete(userId);
    }

    public List<Profile> findAll() {
        return profileRepository.findAllUsers();
    }

    public Page<Profile> findAll(Pageable pageable) {
        return profileRepository.findAllUsers(pageable);
    }

    public Page<Profile> findUsers(Integer genderId, Pageable pageable) {
        return profileRepository.findUsersByGenderId(genderId, pageable);
    }

    public Profile findProfile(Integer id) {
        return profileRepository.findProfileByUserId(id);
    }

    public Page<Profile> findProfiles(String searchName, Integer genderId, Pageable pageable) {
    	return profileRepository.findProfilesBySearchName(searchName, genderId, pageable);
    }

    //ログインユーザーのお気に入り一覧表示
    public Page<Profile> findFavorites(Integer userId, Pageable pageable) {
    	return profileRepository.findFavoriteByUserId(userId, pageable);
    }

}