package com.stonewu.blog.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stonewu.blog.admin.config.TokenManager;
import com.stonewu.blog.admin.config.auth.token.AdminTokenAuthenticationToken;
import com.stonewu.blog.admin.utils.SiteMapUtil;
import com.stonewu.blog.core.entity.Article;
import com.stonewu.blog.core.entity.ArticleTag;
import com.stonewu.blog.core.entity.auth.AdminUserLoginToken;
import com.stonewu.blog.core.entity.custom.ArticleWithMoreInfo;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.service.ArticleService;
import com.stonewu.blog.core.service.ArticleTagLinkService;
import com.stonewu.blog.core.service.ArticleTagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stonewu
 * @since 2018-08-24
 */
@RestController
@RequestMapping("/admin/article")
@CrossOrigin
public class AdminArticleController extends CURDController<Article> {

    @Resource
    private ArticleService articleService;

    @Resource
    private ArticleTagService tagService;
    @Resource
    private ArticleTagLinkService tagLinkService;
    @Resource
    private SiteMapUtil siteMapJob;

    public AdminArticleController(ArticleService service) {
        super(service);
    }

    @Resource
    private TokenManager tokenManager;


    @RequestMapping("/list")
    public ObjectResult list(Model model, Page<ArticleWithMoreInfo> page, ArticleWithMoreInfo param) {
        QueryWrapper<ArticleWithMoreInfo> wrapper = new QueryWrapper<ArticleWithMoreInfo>();
        wrapper.setEntity(param);
        param.setStatus(1);
        page = articleService.selectArticleMoreByPage(page, param, null);
        return new ObjectResult(ApiResultType.SUCCESS, page);
    }

    /**
     * 被逻辑删除的文章
     *
     * @param model
     * @param page
     * @param param
     * @return
     */
    @RequestMapping("/logicDeleteList")
    public ObjectResult logicDeleteList(Model model, Page<ArticleWithMoreInfo> page, ArticleWithMoreInfo param) {
        QueryWrapper<ArticleWithMoreInfo> wrapper = new QueryWrapper<ArticleWithMoreInfo>();
        param.setStatus(2);
        wrapper.setEntity(param);
        page = articleService.selectArticleMoreByPage(page, param, null);
        return new ObjectResult(ApiResultType.SUCCESS, page);
    }

    @RequestMapping("/detail/{id}")
    public ObjectResult list(Model model, @PathVariable(name = "id", required = true) String id) {
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>();
        wrapper.eq("id", id);
        Article article = articleService.getOne(wrapper);
        return new ObjectResult(ApiResultType.SUCCESS, article);
    }

    /**
     * 逻辑删除
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/logicDelete/{id}")
    public ObjectResult logicDelete(Model model, @PathVariable(name = "id", required = true) Integer id) {
        Article entity = new Article();
        entity.setId(id);
        entity.setStatus(2);
        boolean isUpdated = articleService.updateById(entity);
        siteMapJob.createSiteMap();
        if (isUpdated) {
            return new ObjectResult(ApiResultType.SUCCESS);
        }
        return new ObjectResult(ApiResultType.UNKNOW_ERROR, "逻辑删除文章失败");
    }

    /**
     * 还原逻辑删除文章
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/recoverDelete/{id}")
    public ObjectResult recoverDelete(Model model, @PathVariable(name = "id", required = true) Integer id) {
        Article entity = new Article();
        entity.setId(id);
        entity.setStatus(1);
        boolean isUpdated = articleService.updateById(entity);
        if (isUpdated) {
            return new ObjectResult(ApiResultType.SUCCESS);
        }
        return new ObjectResult(ApiResultType.UNKNOW_ERROR, "恢复删除文章失败");
    }

    /**
     * 物理删除
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/physcialDelete/{id}")
    public ObjectResult physcialDelete(Model model, @PathVariable(name = "id", required = true) Integer id) {
        boolean isUpdated = articleService.removeById(id);
        if (isUpdated) {
            return new ObjectResult(ApiResultType.SUCCESS);
        }
        return new ObjectResult(ApiResultType.UNKNOW_ERROR, "物理删除文章失败");
    }

    @Override
    protected void afterAdd(Article bean, Map<String, String> paramMap) {
        String tags = paramMap.get("tags");
        if (StringUtils.isNotBlank(tags)) {
            String[] tagArr = tags.split(",");
            for (String tag : tagArr) {
                ArticleTag param = new ArticleTag();
                param.setTagName(tag);
                ObjectResult result = tagService.insertOrFind(param);
                if (!result.isSuccess()) {
                    continue;
                }
                ArticleTag articleTag = (ArticleTag) result.getData();
                bean = articleService.selectArticleByIdentName(bean.getIdentName(), null);
                tagLinkService.findAndInsert(bean.getId(), articleTag.getId());
            }
        }
    }

    @Override
    protected void afterUpdate(Article bean, Map<String, String> paramMap) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void afterDelete(Integer id, Map<String, String> paramMap) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Article preAdd(Article bean) {
        AdminTokenAuthenticationToken authentication = (AdminTokenAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String authToken = authentication.getToken();
        AdminUserLoginToken token = tokenManager.getTokenForSeries(authToken);
        bean.setAuthorId(token.getUser().getLinkMenberId());
        if(StringUtils.isBlank(bean.getPreviewContent())){
            bean.setPreviewContent("");
        }
        return bean;
    }

    @Override
    protected Article preUpdate(Article bean) {
        bean.setUpdateTime(LocalDateTime.now());
        if(StringUtils.isBlank(bean.getPreviewContent())){
            bean.setPreviewContent("");
        }
        return bean;
    }

    @Override
    protected int preDelete(Integer id) {
        return id;
    }
}
