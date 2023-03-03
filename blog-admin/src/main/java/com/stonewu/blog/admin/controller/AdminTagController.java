package com.stonewu.blog.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stonewu.blog.core.entity.ArticleTag;
import com.stonewu.blog.core.entity.ArticleTagLink;
import com.stonewu.blog.core.entity.custom.ArticleTagInfo;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.service.ArticleTagLinkService;
import com.stonewu.blog.core.service.ArticleTagService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stonewu
 * @since 2018-08-28
 */
@RestController
@RequestMapping("/admin/articleTag")
@CrossOrigin
public class AdminTagController extends CURDController<ArticleTag> {

    @Resource
    private ArticleTagService tagService;
    @Resource
    private ArticleTagLinkService tagLinkService;

    /**
     * 根据文章ID查询标签
     *
     * @param model
     * @param articleId
     * @return
     */
    @RequestMapping("/{articleId}/tag")
    public ObjectResult getTagByArticle(Model model, @PathVariable("articleId") Integer articleId) {
        List<ArticleTagInfo> list = tagService.findTagByArticleId(articleId);
        return new ObjectResult(ApiResultType.SUCCESS, list);
    }

    /**
     * 给文章添加标签
     *
     * @param model
     * @param param
     * @param articleId
     * @return
     */
    @RequestMapping("addTagToArticle")
    @CacheEvict(value = "ArticleTag", allEntries = true)
    public ObjectResult addTagToArticle(Model model, ArticleTag param, Integer articleId) {
        // 先查询是否有该标签，如果没有则新增并且返回
        ObjectResult result = tagService.insertOrFind(param);
        if (!result.isSuccess()) {
            return result;
        }
        ArticleTag tag = (ArticleTag) result.getData();
        return tagLinkService.findAndInsert(articleId, tag.getId());
    }

    /**
     * 给文章添加标签
     *
     * @param model
     * @param param
     * @return
     */
    @RequestMapping("deleteArticleTagLink")
    @CacheEvict(value = "ArticleTag", allEntries = true)
    public ObjectResult deleteTagToArticle(Model model, ArticleTagLink param) {
        // 先查询是否有该标签，如果没有则新增并且返回
        QueryWrapper<ArticleTagLink> wrapper = new QueryWrapper<ArticleTagLink>();
        wrapper.setEntity(param);
        boolean isDelete = tagLinkService.remove(wrapper);
        if (isDelete) {
            return new ObjectResult(ApiResultType.SUCCESS, "删除标签成功");
        }
        return new ObjectResult(ApiResultType.UNKNOW_ERROR, "删除标签失败");
    }

    public AdminTagController(ArticleTagService service) {
        super(service);
    }

    @Override
    protected ArticleTag preAdd(ArticleTag bean) {
        return bean;
    }

    @Override
    protected ArticleTag preUpdate(ArticleTag bean) {
        return bean;
    }

    @Override
    protected int preDelete(Integer id) {
        return id;
    }

    @Override
    protected void afterAdd(ArticleTag bean, Map<String, String> paramMap) {

    }

    @Override
    protected void afterUpdate(ArticleTag bean, Map<String, String> paramMap) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void afterDelete(Integer id, Map<String, String> paramMap) {
        // TODO Auto-generated method stub

    }

}
