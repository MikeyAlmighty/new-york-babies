package com.example.newyorkbabies.configuration;

import com.example.newyorkbabies.model.Baby;
import com.example.newyorkbabies.processor.CSVItemProcessor;
import com.example.newyorkbabies.repository.BabyRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;

@Configuration
public class CSVImporterJob {
    private final CSVItemProcessor csvItemProcessor;

    private final BabyRepository babyRepository;
    private final int CHUNK_SIZE = 50;
    private final int CONCURRENCY_LIMIT = 10;

    public CSVImporterJob(CSVItemProcessor csvItemProcessor, BabyRepository babyRepository) {
        this.csvItemProcessor = csvItemProcessor;
        this.babyRepository = babyRepository;
    }

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("CSVImporterJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .flow(chunkStep(jobRepository, platformTransactionManager))
            .end().build();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("First Chunk", jobRepository)
                .<Baby, Baby>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(flatFileItemReader())
                .processor(csvItemProcessor)
                .writer(itemWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(CONCURRENCY_LIMIT);
        return  asyncTaskExecutor;
    }

    public FlatFileItemReader<Baby> flatFileItemReader() {
        FlatFileItemReader<Baby> itemReader = new FlatFileItemReader<>();

        itemReader.setResource(new FileSystemResource(new File(("/home/mikey/Dev/new-york-babies/src/main/resources/Popular_Baby_Names.csv"))));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());

        return itemReader;
    }

    private LineMapper<Baby> lineMapper() {
        DefaultLineMapper<Baby> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("Year Of Birth", "Gender", "Ethnicity", "First Name", "Count", "Score");

        BeanWrapperFieldSetMapper<Baby> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Baby.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public RepositoryItemWriter<Baby> itemWriter() {
        RepositoryItemWriter<Baby> writer = new RepositoryItemWriter<>();
        writer.setRepository(babyRepository);
        writer.setMethodName("save");
        return writer;
    }
}
