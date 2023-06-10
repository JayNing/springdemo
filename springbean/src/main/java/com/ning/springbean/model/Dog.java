package com.ning.springbean.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * desc:
 * createBy: Ningjianjian
 */
@Data
public class Dog {
    private String name;
    private int age;

    @Autowired
    private Person person;

    @Autowired
    private Cat cat;
}
