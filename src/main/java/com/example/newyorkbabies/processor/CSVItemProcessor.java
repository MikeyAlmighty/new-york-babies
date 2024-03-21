package com.example.newyorkbabies.processor;

import com.example.newyorkbabies.model.BabyDto;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CSVItemProcessor implements ItemProcessor<BabyDto, BabyDto> {

    @Override
    public BabyDto process(BabyDto record) throws Exception {
        System.out.println("BabyRecord: " + record.getFirstName());
        return null;
    }
}
