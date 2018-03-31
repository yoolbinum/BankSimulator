package com.example.demo.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(mappedBy = "profile")
    private AppUser user;

    private int credit_score;

    private BigDecimal credit_line;

    private BigDecimal monthly_income;

    private BigDecimal monthly_spent;

    private BigDecimal available_credit;

    public Profile() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public int getCredit_score() {
        return credit_score;
    }

    public void setCredit_score(int credit_score) {
        this.credit_score = credit_score;
    }

    public BigDecimal getCredit_line() {
        return credit_line;
    }

    public void setCredit_line(BigDecimal credit_line) {
        this.credit_line = credit_line;
    }

    public BigDecimal getMonthly_income() {
        return monthly_income;
    }

    public void setMonthly_income(BigDecimal monthly_income) {
        this.monthly_income = monthly_income;
    }

    public BigDecimal getMonthly_spent() {
        return monthly_spent;
    }

    public void setMonthly_spent(BigDecimal monthly_spent) {
        this.monthly_spent = monthly_spent;
    }

    public BigDecimal getAvailable_credit() {
        return available_credit;
    }

    public void setAvailable_credit(BigDecimal available_credit) {
        this.available_credit = available_credit;
    }
}
