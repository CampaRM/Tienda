package com.brayancampa.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    public String mostrarHome(/*Model model, Principal principal*/){
 //       model.addAttribute("username",principal.getName());
        return "home";
    }

    @GetMapping("/")
    public String redirectToHome(){
        return "redirect:/home";
    }
}