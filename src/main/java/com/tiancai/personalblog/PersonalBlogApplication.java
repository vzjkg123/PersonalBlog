package com.tiancai.personalblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
public class PersonalBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalBlogApplication.class, args);
    }

}
