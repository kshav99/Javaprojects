package com.keshavprojs.Ouath2Demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController


public class UserController {
    @GetMapping("/user-info")
    public Map<String, String> getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("name", principal.getAttribute("name"));
        userInfo.put("email", principal.getAttribute("email"));
        return userInfo;
    }
}