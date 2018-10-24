//ユーザー登録に関するコントローラ

package com.example.konkatsu.web;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.konkatsu.domain.User;
import com.example.konkatsu.service.ProfileService;
import com.example.konkatsu.service.RegisterUserService;

@Controller

public class RegisterUserController {
	@Autowired
	RegisterUserService registerUserService;

	@Autowired
	ProfileService profileService;

	@ModelAttribute   //formからPOSTされた時に値を受け取るクラス,事前にsetUpForm()でインスタンス化をしておけば、RegisterUserFormで設定したフィールドをformで利用することができる
	RegisterUserForm setUpForm(){
		return new RegisterUserForm();
	}

	//loginForm.htmlで"ユーザー登録"を押された時の処理
	@GetMapping(path = "RegisterUserForm")   //path=""　この場合はhttp://localhost:8080/RegisterUserFormとなる
	String registerUserForm() {
        return "konkatsu/registerUserForm";
    }

	//th:action="@{/createUser}"　POSTされた時の処理
	//URLは /createUser/となる
	@PostMapping(path = "createUser")
	String create(@Validated RegisterUserForm form, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {     //入力チェックの結果を確認し、エラーがある場合はユーザー登録画面に戻る
	        return "konkatsu/registerUserForm";
	    }
	    User user = new User();
	    user.setMail(form.getMail());
	    BeanUtils.copyProperties(form, user);//RegisterUserFormをuserにコピーする
		registerUserService.create(user, form.getPass());
		//作成処理が正常に終了した場合はregisterUserConfirm
	    return "konkatsu/registerUserConfirm";
	}
}


