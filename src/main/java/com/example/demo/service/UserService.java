package com.example.demo.service;

import com.example.demo.model.AppUser;
import com.example.demo.model.Role;
import com.example.demo.model.Spending;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.SpendingRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;

@Service
public class UserService {
    final UserRepository userRepository;

    final RoleRepository roleRepository;

    final SpendingRepository spendingRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, SpendingRepository spendingRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.spendingRepository = spendingRepository;
    }

    public AppUser findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void saveNewUser(AppUser user) {
        HashSet<Role> hash = new HashSet<>();
        hash.add(roleRepository.findByRole("USER"));
        user.setRoles(hash);
        userRepository.save(user);
    }

    public void saveUser(AppUser user){
        userRepository.save(user);
    }

    public void addRole(AppUser user, String roleName){
        Role role = roleRepository.findByRole(roleName);
        user.addRole(role);
    }

    public void addSpending(AppUser user, Spending spending){
        user.addSpending(spending);
        saveUser(user);
        saveSpending(spending);
    }

    public void saveSpending(Spending spending){
        spendingRepository.save(spending);
    }

    public Spending findSpendingBySpendingId(long id){
        return spendingRepository.findOne(id);
    }

    public void addMonthlySpent(AppUser user, BigDecimal spent){
        BigDecimal total = user.getProfile().getMonthly_spent().add(spent);
        user.getProfile().setMonthly_spent(total);
        saveUser(user);
    }

    public void updateAvailableCredit(AppUser user){
        BigDecimal credit = user.getProfile().getCredit_line().subtract(user.getProfile().getMonthly_spent());
        user.getProfile().setAvailable_credit(credit);
        saveUser(user);
    }

}