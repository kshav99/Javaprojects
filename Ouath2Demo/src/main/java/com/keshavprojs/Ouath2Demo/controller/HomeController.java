package com.keshavprojs.Ouath2Demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/index.html"; // Serve the static login page
    }

    @GetMapping("/success")
    public String success() {
        return "redirect:/index.html"; // Serve the same page, JS handles success state
    }
}