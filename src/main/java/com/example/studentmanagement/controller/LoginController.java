package com.example.studentmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String displayLogin() {
        return "login";
    }

    @GetMapping("/accessDenied")
    public String displayAccessDeniedPage() {
        return "accessDenied";
    }
}

