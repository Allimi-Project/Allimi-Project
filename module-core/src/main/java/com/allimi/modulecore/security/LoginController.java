package com.allimi.modulecore.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/page")
    public String Page() {
        return "page";
    }
}



