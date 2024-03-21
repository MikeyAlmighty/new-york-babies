package com.example.newyorkbabies.repository;

import com.example.newyorkbabies.model.BabyDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BabyRepository extends JpaRepository<BabyDto,Long> { }
