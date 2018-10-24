//profileテーブルとマッピングさせるエンティティクラス

package com.example.konkatsu.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor    //デフォルトコンストラクタも必要な場合
@AllArgsConstructor   //すべてのフィールドを引数に持つコンストラクタを自動生成
@Entity    //エンティティ（一単位として扱われるデータのまとまり）であることを示す。指定したクラス名がテーブルとマッピングされる
@Table(name = "profile")  //接続するテーブルを指定
public class Profile {

	@Column(name = "user_id")
	@Id   //主キー
	private Integer id;
	private String name;
	private Integer genderId;
	private Date birthday;
	private Integer height;
	private Integer occupationId;
	private Integer income;
	private String text;
	private byte[] image;

	@ManyToOne(fetch = FetchType.LAZY) 		    //@ManyToOneで多対１の関係にする
	@JoinColumn(nullable = true, name = "user_id", insertable=false, updatable=false)   //外部キーのカラム名の指定
	private User user;

	@ManyToOne(fetch = FetchType.LAZY) 		    //@ManyToOneで多対１の関係にする
	@JoinColumn(nullable = true, name = "genderId", insertable=false, updatable=false)   //外部キーのカラム名の指定
	private Gender gender;

	@ManyToOne(fetch = FetchType.LAZY) 		    //@ManyToOneで多対１の関係にする
	@JoinColumn(nullable = true, name = "occupationId", insertable=false, updatable=false)   //外部キーのカラム名の指定
	private Occupation occupation;

					//CascadeType.ALL：所有者側のエンティティクラスのpersist，remove，merge，およびrefreshの操作が関連先にカスケードされる
	@OneToMany(cascade= CascadeType.ALL,mappedBy = "profile")
	private List<Favorite> favorite;




}
