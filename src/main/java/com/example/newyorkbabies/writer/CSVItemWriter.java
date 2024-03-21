package com.example.newyorkbabies.writer;

import com.example.newyorkbabies.model.BabyDto;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class CSVItemWriter implements ItemWriter<BabyDto> {
    @Override
    public void write(Chunk<? extends BabyDto> chunk) throws Exception {

    }
}
