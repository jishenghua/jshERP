package com.jsh.erp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@MapperScan(basePackages = {"com.jsh.erp.datasource.mappers"})
@EnableScheduling
public class ErpApplication{
    public static void main(String[] args) {
        SpringApplication.run(ErpApplication.class, args);
    }
}
