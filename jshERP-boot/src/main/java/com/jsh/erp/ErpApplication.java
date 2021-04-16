package com.jsh.erp;

import com.jsh.erp.utils.ComputerInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@MapperScan("com.jsh.erp.datasource.mappers")
@ServletComponentScan
@EnableScheduling
public class ErpApplication{
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(ErpApplication.class, args);
        Environment environment = context.getBean(Environment.class);
        System.out.println("启动成功，后端服务地址：http://" + ComputerInfo.getIpAddr() + ":"
                + environment.getProperty("server.port") + "，您还需启动前端服务，测试用户：jsh，密码：123456");
    }
}
