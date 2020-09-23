package com.jsh.erp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = {"com.jsh.erp.datasource.mappers"})
@ServletComponentScan
@EnableScheduling
public class ErpApplication{
    public static void main(String[] args) {
        SpringApplication.run(ErpApplication.class, args);
    }
}
