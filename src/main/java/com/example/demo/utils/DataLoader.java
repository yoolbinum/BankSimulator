package com.example.demo.utils;

import com.example.demo.model.AppUser;
import com.example.demo.model.Profile;
import com.example.demo.model.Role;
import com.example.demo.model.Spending;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Override
    public void run(String... strings) throws Exception{
        Role r = new Role();
        r.setRole("USER");
        roleRepository.save(r);

        BigDecimal total = new BigDecimal(0);
        Spending spending = new Spending();
        spending.setAmount(new BigDecimal(12.50));
        spending.setDescription("food");
        userService.saveSpending(spending);
        total = total.add(spending.getAmount());

        Spending spending2 = new Spending();
        spending2.setAmount(new BigDecimal(100));
        spending2.setDescription("Phone Bill");
        userService.saveSpending(spending2);
        total = total.add(spending2.getAmount());

        Spending spending3 = new Spending();
        spending3.setAmount(new BigDecimal(30.50));
        spending3.setDescription("Gas Bill");
        userService.saveSpending(spending3);
        total = total.add(spending3.getAmount());

        Spending spending4 = new Spending();
        spending4.setAmount(new BigDecimal(80.15));
        spending4.setDescription("Electrical Bill");
        userService.saveSpending(spending4);
        total = total.add(spending4.getAmount());

        Spending spending5 = new Spending();
        spending5.setAmount(new BigDecimal(1800.50));
        spending5.setDescription("Housing Bill");
        userService.saveSpending(spending5);
        total = total.add(spending5.getAmount());

        Profile userProfile = new Profile();
        userProfile.setMonthly_income(new BigDecimal(4000));
        userProfile.setCredit_line(new BigDecimal(3000));
        userProfile.setMonthly_spent(total);

        AppUser appUser = new AppUser();
        appUser.setProfile(userProfile);
        appUser.setFirstName("Harry");
        appUser.setLastName("Potter");
        appUser.setPassword("password");
        appUser.setUsername("harry");
        userService.addRole(appUser, "USER");
        appUser.setSpendings(new HashSet<>());
        appUser.addSpending(spending);
        appUser.addSpending(spending2);
        appUser.addSpending(spending3);
        appUser.addSpending(spending4);
        appUser.addSpending(spending5);


        userService.saveUser(appUser);

        userService.updateAvailableCredit(appUser);

    }
}
