//複合主キーを扱いたい場合、別途キーをまとめたクラスを作成する必要がある

package com.example.konkatsu.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class FavoriteKeyId implements Serializable {
	private Integer userId;
	private Integer favoriteUserId;
}
