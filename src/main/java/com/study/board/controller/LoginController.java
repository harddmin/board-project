package com.study.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {

    private final PasswordEncoder passwordEncoder;

    // 로그인
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 회원가입
    @GetMapping("/register")
    public String register(@ModelAttribute("user")) {  // 이때 오브젝트의 이름은 "bkUser"
        return "register";
    }

}
