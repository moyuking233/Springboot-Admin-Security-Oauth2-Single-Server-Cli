package com.nga.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@MapperScan("com.nga.admin.dao")
public class AdminApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(AdminApplication.class,args);
    }
}
