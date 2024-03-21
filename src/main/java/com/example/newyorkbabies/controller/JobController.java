package com.example.newyorkbabies.controller;

import com.example.newyorkbabies.service.JobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job")
public class JobController {

    private final JobService service;

    public JobController(JobService service) {
        this.service = service;
    }


    @GetMapping("/start/{jobName}")
    public String startJob(@PathVariable String jobName) {
        service.startJob(jobName);
        return "Job Started...";
    }
}

