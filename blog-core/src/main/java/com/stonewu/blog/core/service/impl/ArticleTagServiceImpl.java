package com.stonewu.blog.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.stonewu.blog.core.config.BlogCacheConfig;
import com.stonewu.blog.core.entity.ArticleTag;
import com.stonewu.blog.core.entity.custom.ArticleTagInfo;
import com.stonewu.blog.core.entity.custom.TagCountInfo;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.mapper.ArticleTagMapper;
import com.stonewu.blog.core.service.ArticleTagService;
import com.stonewu.blog.core.service.BaseServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stonewu
 * @since 2018-09-05
 */
@Service
public class ArticleTagServiceImpl extends BaseServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
    @Resource
    private ArticleTagMapper tagMapper;

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'findTagByArticleId_'+#id")
    public List<ArticleTagInfo> findTagByArticleId(Integer id) {
        return tagMapper.findTagByArticleId(id);
    }

    @Override
    @CacheEvict(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, allEntries = true)
    public ObjectResult insertOrFind(ArticleTag param) {
        QueryWrapper<ArticleTag> wrapper = new QueryWrapper<ArticleTag>();
        wrapper.eq("tag_name", param.getTagName());
        ArticleTag tag = this.getOne(wrapper);
        if (tag == null) {
            // 未找到tag，新增tag
            boolean isInsert = this.save(param);
            if (!isInsert) {
                // 新增tag失败
                return new ObjectResult(ApiResultType.UNKNOW_ERROR, "新增标签失败");
            }
            // 插入tag后再查询一次
            tag = this.getOne(wrapper);
        }
        return new ObjectResult(ApiResultType.SUCCESS, tag);
    }

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'getMostArtTag'")
    public List<TagCountInfo> getMostArtTag() {
        List<TagCountInfo> mostArtTag = tagMapper.getMostArtTag();
        return mostArtTag;
    }

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'getTagByTagName_'+#tagName")
    public ArticleTag getTagByTagName(String tagName) {
        ArticleTag tag = tagMapper.selectOne(Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getTagName, tagName));
        return tag;
    }
}
