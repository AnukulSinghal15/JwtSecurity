package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String getLoginPage(){
        return "loginPage";
    }

    @GetMapping("postLogin")
    public ModelAndView postLogin(){
        ModelAndView mv= new ModelAndView();
        mv.setViewName("postLoginPage");
        mv.addObject("message", "Login success!");
        return mv;
    }
}
