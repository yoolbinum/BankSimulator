package com.example.demo.repository;

import com.example.demo.model.Spending;
import org.springframework.data.repository.CrudRepository;

public interface SpendingRepository extends CrudRepository<Spending, Long> {

}
