package com.example.demo.controller;

import com.example.demo.model.AppUser;
import com.example.demo.model.Profile;
import com.example.demo.model.Spending;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/spending")
public class SpendingController {

    @Autowired
    private UserService userService;

    private final String spendingDir = "model/spending/";

    @RequestMapping("/list")
    public String getList(Model model, Authentication auth){
        AppUser user = userService.findByUsername(auth.getName());


        model.addAttribute("user", user);
        model.addAttribute("spendings",user.getSpendings());
        return spendingDir + "list";
    }

    @PostMapping("/process")
    public String processForm(@Valid Spending spending, BindingResult result, Authentication auth) {
        if (result.hasErrors()) {
            return spendingDir + "form";
        }
        userService.saveSpending(spending);
        AppUser user = userService.findByUsername(auth.getName());
        user.getSpendings().add(spending);
        userService.saveUser(user);
        userService.addMonthlySpent(user, spending.getAmount());
        userService.updateAvailableCredit(user);
        return "redirect:/spending/list";
    }


    @RequestMapping("/create")
    public String createSpending(Model model, Authentication auth){
        AppUser user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("spending", new Spending());
        return spendingDir + "form";
    }
}
