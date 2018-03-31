package com.example.demo.controller;

import com.example.demo.model.AppUser;
import com.example.demo.model.Profile;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
public class SecurityController {
    private final String secDir = "security/";

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login() {
        return secDir + "login";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new AppUser());
        return secDir + "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(
            @Valid @ModelAttribute("user") AppUser user,
            HttpServletRequest request,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return secDir + "registration";
        }
        Profile profile = new Profile();
        profile.setCredit_line(new BigDecimal(0));
        profile.setCredit_score(0);
        profile.setMonthly_income(new BigDecimal(0));
        profile.setMonthly_spent(new BigDecimal(0));
        user.setProfile(profile);
        userService.saveNewUser(user);
        return "redirect:/login";
    }
}
