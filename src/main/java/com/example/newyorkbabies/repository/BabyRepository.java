package com.example.newyorkbabies.repository;

import com.example.newyorkbabies.model.Baby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BabyRepository extends JpaRepository<Baby,String> { }
