package com.example.newyorkbabies.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString
public class BabyDto {
    private int yearOfBirth;
    private String gender;
    private String ethnicity;
    private String firstName;
    private Integer count;
    private Integer rank;
}

