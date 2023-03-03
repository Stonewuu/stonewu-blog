package com.stonewu.blog.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stonewu.blog.core.entity.ArticleTag;
import com.stonewu.blog.core.entity.Menber;
import com.stonewu.blog.core.entity.custom.ArticleWithMoreInfo;
import com.stonewu.blog.core.service.ArticleService;
import com.stonewu.blog.core.service.ArticleTagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stonewu
 * @since 2018-08-24
 */
@Controller
@RequestMapping("/tag")
public class ArticleTagController extends AbstractController {

    @Resource
    private ArticleTagService tagService;
    @Resource
    private ArticleService articleService;

    @RequestMapping(value = {"/{tagName}", "/{tagName}/page/{pageNum}"})
    public String article(HttpSession session, Model model, @PathVariable(name = "tagName") String tagName, @PathVariable(name = "pageNum", required = false) Integer pageNum) {
        Menber menberInfo = (Menber) session.getAttribute("menberInfo");
        Page<ArticleWithMoreInfo> page = new Page<>(1, 10);
        if (pageNum != null && pageNum > 0) {
            page.setCurrent(pageNum);
        }
        Page<ArticleWithMoreInfo> article = articleService.selectArticleByTagName(page, tagName, menberInfo);
        if(article.getRecords().isEmpty()){
            return noFound(model);
        }
        ArticleTag tag = tagService.getTagByTagName(tagName);
        model.addAttribute("articles", article);
        model.addAttribute("tag", tag);
        model.addAttribute("isTag", true);
        return index(model);
    }

}

