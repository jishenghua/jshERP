package com.jsh.erp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.File;

//@Configuration
public class WebConfig {
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Configuration
    public static class FrontEnd implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
        @Value("${web.front.baseDir}")
        private File baseDir;

        @Override
        public void customize(ConfigurableServletWebServerFactory factory) {
            if (!baseDir.exists()) {
                if (!baseDir.mkdir()) {
                    logger.info("create web.front base path:" + baseDir + " failed!already exists!");
                } else {
                    logger.info("create web.front base path:" + baseDir + " success!");
                }
            }
            factory.setDocumentRoot(baseDir);
        }
    }
}