package com.stonewu.blog.web.controller;

import com.stonewu.blog.core.entity.Bloginfo;
import com.stonewu.blog.core.entity.Blogsetting;
import com.stonewu.blog.core.entity.Category;
import com.stonewu.blog.core.entity.Navigation;
import com.stonewu.blog.core.entity.custom.ArticleWithMoreInfo;
import com.stonewu.blog.core.entity.custom.TagCountInfo;
import com.stonewu.blog.core.entity.custom.YearGroup;
import com.stonewu.blog.core.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
public abstract class AbstractController {
    @Resource
    private NavigationService navigationService;
    @Resource
    private ArticleTagService articleTagService;
    @Resource
    private ArticleService articleService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private BloginfoService bloginfoService;
    @Resource
    private BlogsettingService blogsettingService;

    public String noFound(Model model) {
        fillData(model);
        return "404";
    }

    public String index(Model model) {
        fillData(model);
        return "index";
    }

    public String article(Model model) {
        fillData(model);
        return "article";
    }

    private void fillData(Model model) {
        // 导航
        List<Navigation> navigations = navigationService.list();
        model.addAttribute("navigations", navigations);
        // 分类
        List<Category> categorys = categoryService.list();
        model.addAttribute("categorys", categorys);
        // 最近发布文章
        List<ArticleWithMoreInfo> recentArticle = articleService.selectArticleOrderCreateTime();
        model.addAttribute("recentArticle", recentArticle);
        // 文章归档
        List<YearGroup> yearGroups = articleService.selectGroupByCreateTime();
        model.addAttribute("yearGroups", yearGroups);
        // 最近被回复的文章
        List<ArticleWithMoreInfo> recentReplyArticle = articleService.selectArticleOrderReplyTime();
        model.addAttribute("recentReplyArticle", recentReplyArticle);
        // 标签
        List<TagCountInfo> mostArtTag = articleTagService.getMostArtTag();
        model.addAttribute("topTags", mostArtTag);
        // 博客信息
        Bloginfo activeInfo = bloginfoService.getActiveInfo();
        model.addAttribute("activeInfo", activeInfo);
        // 博客设置
        Blogsetting activeSetting = blogsettingService.getActiveSetting();
        model.addAttribute("activeSetting", activeSetting);
        model.addAttribute("now", new Date());
    }

}
