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
    String DIRECTION = "ASC";

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

    public Page<Baby> findAllBabiesWithPaginationAndSorting(int offset, int pageSize, String field, String isAscending) {
        Sort sort = isAscending.equals(DIRECTION)
            ? Sort.by(field).ascending()
            : Sort.by(field).descending();

        return babyRepository.findAll(PageRequest.of(offset, pageSize).withSort(sort));
    }

    public Page<Baby> findBabiesLike(String searchTerm, int offset, int pageSize) {
        return babyRepository.findByFirstNameLike(searchTerm, PageRequest.of(offset, pageSize));
    }
}
