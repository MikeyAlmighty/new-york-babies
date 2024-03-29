package com.example.newyorkbabies.processor;

import com.example.newyorkbabies.model.Baby;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CSVItemProcessor implements ItemProcessor<Baby, Baby> {

    @Override
    public Baby process(Baby baby) throws Exception {
        baby.setId(UUID.randomUUID().toString());
        System.out.println("Baby: " + baby.toString());
        return baby;
    }
}
