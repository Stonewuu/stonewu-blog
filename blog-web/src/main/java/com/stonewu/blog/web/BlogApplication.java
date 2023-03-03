package com.stonewu.blog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication()
@ComponentScan(basePackages = {"com.stonewu.blog.core.config","com.stonewu.blog.core.properties", "com.stonewu.blog.core.entity", "com.stonewu.blog.core.service", "com.stonewu.blog.core.mapper", "com.stonewu.blog.web.config", "com.stonewu.blog.web.controller", "com.stonewu.blog.web.filter"})
@ServletComponentScan(basePackages = {"com.stonewu.blog.web.filter"})
public class BlogApplication {

    private static final Logger logger = LoggerFactory.getLogger(BlogApplication.class);

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(BlogApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        logger.info("博客前台已启动");
    }
}
