package com.stonewu.blog.web.controller;


import com.stonewu.blog.core.entity.Category;
import com.stonewu.blog.core.entity.Menber;
import com.stonewu.blog.core.entity.custom.ArticleTagInfo;
import com.stonewu.blog.core.entity.custom.ArticleWithMoreInfo;
import com.stonewu.blog.core.service.ArticleService;
import com.stonewu.blog.core.service.ArticleTagService;
import com.stonewu.blog.core.service.CategoryService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stonewu
 * @since 2018-08-24
 */
@Controller
@RequestMapping("/article")
public class ArticleController extends AbstractController {

    @Resource
    private ArticleTagService tagService;
    @Resource
    private ArticleService articleService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @RequestMapping("/{artCode}")
    public String article(HttpSession session, Model model, @PathVariable(name = "artCode") String artCode) {
        Menber menberInfo = (Menber) session.getAttribute("menberInfo");
        ArticleWithMoreInfo article = articleService.selectArticleByIdentName(artCode, menberInfo);
        if (article == null || article.getId() == null) {
            return noFound(model);
        }
        redisTemplate.opsForValue().increment("article_" + article.getId(), 1);
        List<ArticleTagInfo> list = tagService.findTagByArticleId(article.getId());
        Category category = categoryService.selectByIdentName(article.getClassIdentName());
        model.addAttribute("article", article);
        model.addAttribute("currCategory", category);
        model.addAttribute("tags", list);
        model.addAttribute("isDetail", true);
        return article(model);
    }

}

