package com.stonewu.blog.admin.config;

import com.stonewu.blog.admin.utils.SiteMapUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class InitRunner implements ApplicationRunner {
    @Resource
    private SiteMapUtil siteMapJob;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        siteMapJob.createSiteMap();
    }

}
