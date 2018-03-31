package com.example.demo.controller;

import com.example.demo.model.AppUser;
import com.example.demo.model.Profile;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;

    private final String profileDir = "model/profile/";

    @RequestMapping("/detail")
    public String getProfile(   Model model, Authentication auth){
        AppUser user = userService.findByUsername(auth.getName());
        userService.updateCreditScore(user);
        model.addAttribute("user", user);
        model.addAttribute("profile", user.getProfile());
        return profileDir + "detail";
    }

    @PostMapping("/process")
    public String processForm(@Valid Profile profile, BindingResult result, Authentication auth) {
        if (result.hasErrors()) {
            return profileDir + "form";
        }
        AppUser user = userService.findByUsername(auth.getName());
        Profile userProfile = user.getProfile();
        userProfile.setMonthly_income(profile.getMonthly_income());
        userProfile.setCredit_line(profile.getCredit_line());
        user.setProfile(userProfile);
        userService.saveUser(user);
        userService.updateAvailableCredit(user);
        return "redirect:/profile/detail";
    }

    @RequestMapping("/update")
    public String updateProfile(Model model, Authentication auth) {
        AppUser user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("profile", user.getProfile());
        return profileDir + "form";
    }
}
