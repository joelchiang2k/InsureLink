package com.synex.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class InsuranceController {    
    @RequestMapping("Home")
    public String insuranceHomePage(Model model, Principal principal) {
        String loggedInUser = principal != null ? principal.getName() : "Guest";
        model.addAttribute("loggedInUser", loggedInUser);
        return "home";
    }    
}

