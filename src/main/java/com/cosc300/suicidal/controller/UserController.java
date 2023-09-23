package com.cosc300.suicidal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/chatbox")
    public  String chatbox(){
        return "chat_box";
    }

//    @PostMapping("/register")
//    public String registerUser(User user) {
//        userService.saveUser(user);
//        return "login_html";
//    }

    @GetMapping("/register")
    public String register(Model model, @RequestParam(required = false) String m) {
        model.addAttribute("msg", m);
        return "registration_html";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login_html";
    }

    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "confirm";
    }

    @GetMapping("/confirmed")
    public String confirmed() {
        return "confirmed";
    }

    @GetMapping("/reset/password") 
    public String reset() {
        return "password_reset";
    }
}
