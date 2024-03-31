package com.example.newyorkbabies.controller;

import com.example.newyorkbabies.dto.APIResponse;
import com.example.newyorkbabies.model.Baby;
import com.example.newyorkbabies.service.BabyService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baby")
public class BabyController {
    private final BabyService babyService;

    public BabyController(BabyService babyService) {
        this.babyService = babyService;
    }

    @GetMapping
    //    ALL
    public APIResponse<List<Baby>> getBabies() {
        List<Baby> allBabies = babyService.findAllBabies();
        return new APIResponse<>(allBabies.size(), allBabies);
    }

    //    SORTING
    @GetMapping("/{field}")
    public APIResponse<List<Baby>> getBabiesWithSorting(@PathVariable String field) {
        List<Baby> allBabiesWithSorting = babyService.findAllBabiesWithSorting(field);
        return new APIResponse<>(allBabiesWithSorting.size(), allBabiesWithSorting);
    }

    //    PAGINATING
    @GetMapping("/pagination/{offset}/{pageSize}")
    public APIResponse<Page<Baby>> getBabiesWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<Baby> allBabiesWithPagination = babyService.findAllBabiesWithPagination(offset, pageSize);
        return new APIResponse<>(allBabiesWithPagination.getSize(), allBabiesWithPagination);
    }

    //    SORTING AND PAGINATING
    @GetMapping("/pagination/{offset}/{pageSize}/{field}/{direction}")
    public APIResponse<Page<Baby>> getBabiesWithPaginationAndSorting(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field , @PathVariable String direction) {
        Page<Baby> allBabiesWithPaginationAndSorting = babyService.findAllBabiesWithPaginationAndSorting(offset, pageSize, field, direction);
        return new APIResponse<>(allBabiesWithPaginationAndSorting.getSize(), allBabiesWithPaginationAndSorting);
    }

    @GetMapping("/search")
    public APIResponse<List<Baby>> getBabiesLikeSearch(@RequestParam String term) {
        List<Baby> babies = babyService.findBabiesLike(term);
        return new APIResponse<>(babies.size(), babies);
    }
}
