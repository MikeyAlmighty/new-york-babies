package com.example.newyorkbabies.repository;

import com.example.newyorkbabies.model.Baby;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BabyRepository extends JpaRepository<Baby,String> {
    @Query(value = "SELECT b from Baby b WHERE b.firstName LIKE concat('%', :query, '%')")
    Page<Baby> findByFirstNameLike(String query, Pageable pageable);
}
