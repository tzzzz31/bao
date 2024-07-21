package com.bao.bookforum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bao.bookforum.mapper")
public class BookForumApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookForumApplication.class, args);
    }

}
