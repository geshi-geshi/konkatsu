package com.example.konkatsu.domain;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data  //getterメソッド・setterメソッドをコード上に直接書かなくて済む
@NoArgsConstructor
@AllArgsConstructor
@Entity  //JPAに管理してもらう
@Table(name = "occupation")  //接続するテーブルを指定
@ToString(exclude = "profile")
public class Occupation {


	// データ型 serial（PostgreSQL）
    @Id  //変数idをプライマリーキーに指定
    private Integer occupationId;
    private String occupationName;


    @OneToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL,mappedBy = "occupation") //occupationとprofileを1対多の関係にするため@OneToManyをつけるcascade = CascadeType.ALLを設定することでUserの操作をprofileにも伝播できる
    private List<Profile> profile;

}


