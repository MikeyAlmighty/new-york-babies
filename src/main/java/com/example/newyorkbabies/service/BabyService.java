package com.example.newyorkbabies.service;

import com.example.newyorkbabies.model.Baby;
import com.example.newyorkbabies.repository.BabyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BabyService {
    private final BabyRepository babyRepository;

    public BabyService(BabyRepository babyRepository) {
        this.babyRepository = babyRepository;
    }

    public List<Baby> findAllBabies() {
       return babyRepository.findAll();
    }

    public List<Baby> findAllBabiesWithSorting(String field) {
        return babyRepository.findAll(Sort.by(field));
    }

    public Page<Baby> findAllBabiesWithPagination(int offset, int pageSize) {
        return babyRepository.findAll(PageRequest.of(offset, pageSize));
    }

    public Page<Baby> findAllBabiesWithPaginationAndSorting(int offset, int pageSize, String field) {
        return babyRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
    }
}
