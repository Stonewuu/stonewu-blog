package com.stonewu.blog.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stonewu.blog.core.entity.Menber;
import com.stonewu.blog.core.entity.custom.ArticleWithMoreInfo;
import com.stonewu.blog.core.service.ArticleService;
import com.stonewu.blog.core.service.CategoryService;
import com.stonewu.blog.core.service.NavigationService;
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
@RequestMapping("")
public class MainController extends AbstractController {
    @Resource
    private ArticleService articleService;
    @Resource
    private NavigationService navigationService;
    @Resource
    private CategoryService categoryService;


    @RequestMapping(value = {"/index", "/", "/page/{pageNum}"})
    public String index(HttpSession session, Model model, @PathVariable(name = "pageNum", required = false) Integer pageNum) {
        ArticleWithMoreInfo param = new ArticleWithMoreInfo();
        param.setStatus(1);
        Page<ArticleWithMoreInfo> page = new Page<>(1, 10);
        if (pageNum != null && pageNum > 0) {
            page.setCurrent(pageNum);
        }
        Menber menberInfo = (Menber) session.getAttribute("menberInfo");
        page = articleService.selectArticleMoreByPage(page, param, menberInfo);
        if(page.getRecords().isEmpty()){
            return noFound(model);
        }
        model.addAttribute("articles", page);
        model.addAttribute("isIndex", true);
        return index(model);
    }

    @RequestMapping(value = {"/search/{keywords}", "/search/{keywords}/page/{pageNum}"})
    public String search(HttpSession session, Model model, @PathVariable(name = "keywords") String keywords, @PathVariable(name = "pageNum", required = false) Integer pageNum) {
        ArticleWithMoreInfo param = new ArticleWithMoreInfo();
        Page<ArticleWithMoreInfo> page = new Page<>(1, 10);
        if (pageNum != null && pageNum > 0) {
            page.setCurrent(pageNum);
        }
        param.setTitle(keywords);
        param.setStatus(1);
        Menber menberInfo = (Menber) session.getAttribute("menberInfo");
        page = articleService.selectArticleMoreByPage(page, param, menberInfo);
        if(page.getRecords().isEmpty()){
            return noFound(model);
        }
        model.addAttribute("articles", page);
        model.addAttribute("isSearch", true);
        model.addAttribute("keywords", keywords);
        return index(model);
    }

}

