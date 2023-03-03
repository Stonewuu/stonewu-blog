package com.stonewu.blog.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stonewu.blog.core.entity.Menber;
import com.stonewu.blog.core.entity.custom.ArticleWithMoreInfo;
import com.stonewu.blog.core.service.ArticleService;
import com.stonewu.blog.core.service.ArticleTagService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.redis.core.RedisTemplate;
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
@RequestMapping("/group")
public class GroupController extends AbstractController {

    @Resource
    private ArticleTagService tagService;
    @Resource
    private ArticleService articleService;
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @RequestMapping(value = {"/{year}", "/{year}/page/{pageNum}"})
    public String article(HttpSession session, Model model, @PathVariable(name = "year", required = true) String year, @PathVariable(name = "pageNum", required = false) Integer pageNum) {
        if(!NumberUtils.isCreatable(year)){
            return noFound(model);
        }
        ArticleWithMoreInfo param = new ArticleWithMoreInfo();
        param.setStatus(1);
        param.setYear(NumberUtils.toInt(year));
        Page<ArticleWithMoreInfo> page = new Page<>(1, 10);
        if (pageNum != null && pageNum > 0) {
            page.setCurrent(pageNum);
        }
        Menber menberInfo = (Menber) session.getAttribute("menberInfo");
        page = articleService.selectArticleMoreByPage(page, param, menberInfo);
        if (page.getRecords().isEmpty()) {
            return noFound(model);
        }
        model.addAttribute("articles", page);
        model.addAttribute("year", year);
        model.addAttribute("isGroup", true);
        return index(model);
    }

}

