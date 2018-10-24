//ユーザー登録に関するクラス
//ユーザー登録htmlの<form>から送るパラメータをマッピングさせる

package com.example.konkatsu.web;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegisterUserForm {
		@NotNull
		private String mail;
		@NotNull
		private String pass;

		// テスト１からの修正
}
