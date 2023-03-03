package com.stonewu.blog.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@ComponentScan(basePackages = {"com.stonewu.blog.core.config", "com.stonewu.blog.core.properties", "com.stonewu.blog.core.entity", "com.stonewu.blog.core.service", "com.stonewu.blog.core.mapper", "com.stonewu.blog.admin.config", "com.stonewu.blog.admin.controller", "com.stonewu.blog.admin.utils"})
public class AdminApplication {

    private static final Logger logger = LoggerFactory.getLogger(AdminApplication.class);

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(AdminApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        logger.info("后台管理服务已启动");
    }
}
