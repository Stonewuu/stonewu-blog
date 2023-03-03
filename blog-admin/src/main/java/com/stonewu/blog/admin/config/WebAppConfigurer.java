package com.stonewu.blog.admin.config;

import com.stonewu.blog.core.entity.properties.BlogConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurationSupport {
    @Resource
    private BlogConfig config;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// 设置允许跨域的路径
                .allowedOrigins(config.getFrontServer())// 设置允许跨域请求的域名
                .allowCredentials(true)// 是否允许证书 不再默认开启
                .allowedMethods("GET", "POST", "PUT", "DELETE")// 设置允许的方法
                .maxAge(3600);// 跨域允许时间
    }

}
