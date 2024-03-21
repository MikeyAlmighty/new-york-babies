package com.example.newyorkbabies.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    JobLauncher jobLauncher;


    @Qualifier("csvJob")
    Job csvImporterJob;

    public JobService(JobLauncher jobLauncher, Job csvImporterJob) {
        this.jobLauncher = jobLauncher;
        this.csvImporterJob = csvImporterJob;
    }

    @Async
    public void startJob(String jobName) {
        var jobParameters = new JobParametersBuilder()
            .addJobParameter("currentTime", System.currentTimeMillis(), Long.class)
            .toJobParameters();

        try {
            JobExecution jobExecution = null;
            if (jobName.equals("CSVImporterJob")) {
                jobLauncher.run(csvImporterJob, jobParameters);
            }
            System.out.println("jobExecutionId = " + jobExecution.getId());
        } catch (Exception exception) {
            System.out.println("Error: Exception while trying to start job: " + exception);
        }
    }
}
