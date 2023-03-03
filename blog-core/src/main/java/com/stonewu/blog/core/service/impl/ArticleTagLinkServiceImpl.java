package com.stonewu.blog.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stonewu.blog.core.config.BlogCacheConfig;
import com.stonewu.blog.core.entity.ArticleTagLink;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.mapper.ArticleTagLinkMapper;
import com.stonewu.blog.core.service.ArticleTagLinkService;
import com.stonewu.blog.core.service.BaseServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stonewu
 * @since 2018-09-05
 */
@Service
public class ArticleTagLinkServiceImpl extends BaseServiceImpl<ArticleTagLinkMapper, ArticleTagLink>
        implements ArticleTagLinkService {

    @Override
    @CacheEvict(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, allEntries = true)
    public ObjectResult findAndInsert(Integer articleId, Integer tagId) {
        // 拼装查询条件
        ArticleTagLink tagLink = new ArticleTagLink();
        tagLink.setArticleId(articleId);
        tagLink.setTagId(tagId);
        QueryWrapper<ArticleTagLink> tagwrapper = new QueryWrapper<ArticleTagLink>(tagLink);
        // 查询该文章编号和标签ID是否已经关联
        ArticleTagLink tagLinkResult = this.getOne(tagwrapper);
        if (tagLinkResult != null) {
            return new ObjectResult(ApiResultType.UNKNOW_ERROR, "新增标签关联失败，关联已存在");
        }
        // 插入是否成功
        boolean flag = this.save(tagLink);
        if (flag) {
            return new ObjectResult(ApiResultType.SUCCESS, tagLink);
        }
        return new ObjectResult(ApiResultType.UNKNOW_ERROR, "新增标签关联失败");
    }

}
