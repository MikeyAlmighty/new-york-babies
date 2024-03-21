package com.example.newyorkbabies.configuration;

import com.example.newyorkbabies.model.BabyDto;
import com.example.newyorkbabies.processor.CSVItemProcessor;
import com.example.newyorkbabies.repository.BabyRepository;
import com.example.newyorkbabies.writer.CSVItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;

@Configuration
public class CSVImporterJob {
    private final CSVItemProcessor csvItemProcessor;

    private final BabyRepository babyRepository;

    public CSVImporterJob(CSVItemProcessor csvItemProcessor, BabyRepository babyRepository) {
        this.csvItemProcessor = csvItemProcessor;
        this.babyRepository = babyRepository;
    }

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("CSVImporterJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(chunkStep(jobRepository, platformTransactionManager))
            .build();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("First Chunk", jobRepository)
                .<BabyDto, BabyDto>chunk(3, platformTransactionManager)
                .reader(flatFileItemReader())
                .processor(csvItemProcessor)
                .writer(writer())
                .build();
    }

    public FlatFileItemReader<BabyDto> flatFileItemReader() {
        FlatFileItemReader<BabyDto> flatFileItemReader = new FlatFileItemReader<BabyDto>();

        flatFileItemReader.setResource(new FileSystemResource(new File(("/home/mikey/Dev/new-york-babies/src/main/resources/Popular_Baby_Names.csv"))));
        flatFileItemReader.setLineMapper(new DefaultLineMapper<>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("Year Of Birth", "Gender", "Ethnicity", "First Name", "Count", "Rank");
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<BabyDto>() {
                    {
                        setTargetType(BabyDto.class);
                    }
                });
            }
        });

        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setStrict(false);

        return flatFileItemReader;
    }

    private ItemWriter<? super BabyDto> writer() {
        return babyRepository::saveAll;
    }
}
