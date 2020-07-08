package com.example.air;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jleo
 * @date 2020/7/2
 */
@SpringBootApplication
@MapperScan("com.example.air.mapper.*")
public class AirApplication {
    public static void main(String[] args) {
        SpringApplication.run(AirApplication.class, args);
    }
}
