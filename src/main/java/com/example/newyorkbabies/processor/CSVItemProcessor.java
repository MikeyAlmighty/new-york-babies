package com.example.newyorkbabies.processor;

import com.example.newyorkbabies.model.Baby;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CSVItemProcessor implements ItemProcessor<Baby, Baby> {

    @Override
    public Baby process(Baby baby) throws Exception {
        System.out.println("Baby: " + baby.toString());
        return baby;
    }
}
