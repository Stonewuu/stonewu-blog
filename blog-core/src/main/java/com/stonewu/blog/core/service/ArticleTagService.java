package com.stonewu.blog.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stonewu.blog.core.config.BlogCacheConfig;
import com.stonewu.blog.core.entity.ArticleTag;
import com.stonewu.blog.core.entity.custom.ArticleTagInfo;
import com.stonewu.blog.core.entity.custom.TagCountInfo;
import com.stonewu.blog.core.entity.result.ObjectResult;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;



/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stonewu
 * @since 2018-09-05
 */
public interface ArticleTagService extends IService<ArticleTag> {

    /**
     * 按文章ID查询标签列表
     *
     * @param id
     * @return
     */
    List<ArticleTagInfo> findTagByArticleId(Integer id);

    /**
     * 如果没有该标签就插入，否则直接返回
     *
     * @param param
     * @return
     */
    ObjectResult insertOrFind(ArticleTag param);

    List<TagCountInfo> getMostArtTag();

    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'getTagByTagName_'+#tagName")
    ArticleTag getTagByTagName(String tagName);
}
