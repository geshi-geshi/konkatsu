//ログインに関するコントローラ

package com.example.konkatsu.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
public class LoginController {

    @RequestMapping
    public String loginForm() {
        return "konkatsu/loginForm";
    }
    //　

    //

// saaa

}