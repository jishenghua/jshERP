package com.jsh.erp.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class DbConfig {
    private static final Logger logger = LoggerFactory.getLogger(DbConfig.class);

    @Bean(name = "erpDatasource")
    @Primary
    public DataSource erpDatasource(ErpDatasourceProperties properties){
        try {
            DruidDataSource datasource = new DruidDataSource();
            datasource.setDriverClassName(properties.driverClassName);
            datasource.setUrl(properties.url);
            datasource.setUsername(properties.username);
            datasource.setPassword(properties.password);
            datasource.setInitialSize(1);
            datasource.setMinIdle(1);
            datasource.setMaxWait(60000);
            datasource.setMaxActive(5);
            datasource.setTimeBetweenEvictionRunsMillis(60000);
            datasource.setValidationQuery("select '1'");
            datasource.setTestOnBorrow(false);
            datasource.setTestOnReturn(false);
            datasource.setTestWhileIdle(true);
            datasource.setPoolPreparedStatements(true);
            datasource.setMaxOpenPreparedStatements(20);
            datasource.setMinEvictableIdleTimeMillis(300000);
            datasource.init();
            return datasource;
        }catch (Exception e){
            logger.error("服务启动失败，jsh_erp数据库Datasource初始化失败:"+e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }

    @Bean
    @Primary
    public JdbcTemplate jdbcTemplate(@Qualifier("erpDatasource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Configuration
    @ConfigurationProperties(prefix = "erpDatasource")
    public static class ErpDatasourceProperties {
        private String driverClassName;
        private String url;
        private String username;
        private String password;

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
