package com.stonewu.blog.admin.utils;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import com.stonewu.blog.core.entity.Article;
import com.stonewu.blog.core.entity.Blogsetting;
import com.stonewu.blog.core.entity.Category;
import com.stonewu.blog.core.entity.properties.BlogConfig;
import com.stonewu.blog.core.service.ArticleService;
import com.stonewu.blog.core.service.BlogsettingService;
import com.stonewu.blog.core.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 生成sitemap
 */
@Component
@EnableScheduling
public class SiteMapUtil {
    private static final Logger logger = LoggerFactory.getLogger(SiteMapUtil.class);

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Resource
    private ArticleService articleService;
    @Resource
    private CategoryService categoryService;

    @Resource
    private BlogConfig blogConfig;
    @Resource
    private BlogsettingService blogsettingService;

//    @Scheduled(cron = "0/10 * * * * ?")
    @Scheduled(cron = "0 0 0 * * ?")
    public void createSiteMap() {
        logger.info("开始生成sitemap文件...");
        Blogsetting activeSetting = blogsettingService.getActiveSetting();
        try {
            long start = System.currentTimeMillis();
            String WEBSITE = activeSetting.getBlogDomain();
            /** 存储位置,实际应用项目中，需要指到网站的根目录 request.getSession().getServletContext().getRealPath("/") **/
            String realPath = blogConfig.getUploadPath();

            List<Article> articleList = articleService.list(Wrappers.<Article>lambdaQuery().eq(Article::getStatus, 1));
            //start ======================================================================
            String path = realPath + "/sitemap/";
            fileExists(path);//判断文件夹是否存在，不存在则创建
            WebSitemapGenerator sitemapGenerator = WebSitemapGenerator.builder(WEBSITE, new File(path)).gzip(false).build();
            WebSitemapUrl sitemapUrl = new WebSitemapUrl.Options(WEBSITE).lastMod(LocalDateTime.now().format(formatter)).priority(1.0).changeFreq(ChangeFreq.HOURLY).build();
            sitemapGenerator.addUrl(sitemapUrl);

            List<Category> categoryList = categoryService.list();
            for (Category category : categoryList) {
                //遍历分类
                WebSitemapUrl sitemap = new WebSitemapUrl.Options(WEBSITE + "/category/" + category.getIdentName()).lastMod(LocalDateTime.now().format(formatter)).priority(0.8).changeFreq(ChangeFreq.HOURLY).build();
                sitemapGenerator.addUrl(sitemap);
            }

            for (Article article : articleList) {
                //遍历文章
                WebSitemapUrl sitemap = new WebSitemapUrl.Options(WEBSITE + "/article/" + article.getIdentName()).lastMod(article.getUpdateTime().format(formatter)).priority(0.9).changeFreq(ChangeFreq.HOURLY).build();
                sitemapGenerator.addUrl(sitemap);
            }
            sitemapGenerator.write();
            long end = System.currentTimeMillis();
            logger.info("生成Sitemap完毕, 共耗时：{} 毫秒", (end - start));
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    public static void fileExists(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
