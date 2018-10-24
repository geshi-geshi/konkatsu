//このクラスにプロフィール登録画面の<form>から送るパラメータをマッピングさせる


package com.example.konkatsu.web;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProfileForm implements Serializable{


    @Column(name = "user_id")
    @Id   //主キー
    //@GeneratedValue(strategy = GenerationType.IDENTITY)    //自動採番される

    private Integer id;


    //Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //Integer userId = ((User)principal).getUserId();

    @NotNull
    private String name;
    @NotNull
    Integer genderId;
    @NotNull
    private Date birthday;
    @NotNull
    private Integer height;
    @NotNull
    private Integer occupationId;
    @NotNull
    private Integer income;
    @NotNull
    private String text;
    @NotNull
    private MultipartFile file;
    //ファイルアップロードをするにはMultipartFile
    //private byte[] image;



}