package com.example.newyorkbabies.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "baby_info")
public class Baby {
    @Column(name = "YEAR_OF_BIRTH")
    private int yearOfBirth;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "ETHNICITY")
    private String ethnicity;

    @Id
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "COUNT")
    private Integer count;

    @Column(name = "SCORE")
    private Integer score;
}

