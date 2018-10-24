package com.example.konkatsu.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //全てのフィールドを引数に受け取るコンストラクタを自動生成できる
@AllArgsConstructor //すべてのフィールドを引数に持つコンストラクタを自動生成
@Entity
@Table(name = "favorite")
@IdClass(value=FavoriteKeyId.class)  //複合主キーを扱いたい場合、別途キーをまとめたクラスを作成する必要がある

public class Favorite {

	@Id
	@Column(name = "user_id")
	private Integer userId;

	@Id
	@Column(name = "favorite_user_id")
	private Integer favoriteUserId;

	//FavoriteとUserをMapping
	//cascadeはDB操作を実行する方に設定
	//LAZY:データが必要になりフィールドにアクセスした時、初めて読み込む機能
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = true, name = "user_id", insertable=false, updatable=false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = true, name = "favorite_user_id", insertable=false, updatable=false)
	private Profile profile;


}
