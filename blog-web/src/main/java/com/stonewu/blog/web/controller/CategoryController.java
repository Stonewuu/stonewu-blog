package com.stonewu.blog.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stonewu.blog.core.entity.Category;
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

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController {
    @Resource
    private ArticleService articleService;
    @Resource
    private NavigationService navigationService;
    @Resource
    private CategoryService categoryService;

    @RequestMapping(value = {"/{categoryName}", "/{categoryName}/page/{pageNum}"})
    public String category(HttpSession session, Model model, @PathVariable(name = "categoryName") String categoryName, @PathVariable(name = "pageNum", required = false) Integer pageNum) {
        ArticleWithMoreInfo param = new ArticleWithMoreInfo();
        param.setStatus(1);
        Page<ArticleWithMoreInfo> page = new Page<>(1, 10);
        if (pageNum != null) {
            page.setCurrent(pageNum);
        }
        Category category = categoryService.selectByIdentName(categoryName);
        if (category == null) {
            return noFound(model);
        }
        model.addAttribute("currCategory", category);
        param.setCategoryId(category.getId());
        Menber menberInfo = (Menber) session.getAttribute("menberInfo");
        page = articleService.selectArticleMoreByPage(page, param, menberInfo);
        model.addAttribute("articles", page);
        model.addAttribute("isCategory", true);
        return index(model);
    }

}
