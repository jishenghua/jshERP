package com.jsh.erp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.io.File;

@Configuration
public class WebConfig {
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Configuration
    @ConfigurationProperties(prefix = "web.front")
    public static class FrontEnd implements EmbeddedServletContainerCustomizer {
        private File baseDir;

        public File getBaseDir() {
            return baseDir;
        }

        public void setBaseDir(File baseDir) {
            this.baseDir = baseDir;
        }

        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            if (!baseDir.exists()) {
                if (!baseDir.mkdir()) {
                    logger.info("create web.front base path:" + baseDir + " failed!already exists!");
                } else {
                    logger.info("create web.front base path:" + baseDir + " success!");
                }
            }
            container.setDocumentRoot(baseDir);
        }
    }
}