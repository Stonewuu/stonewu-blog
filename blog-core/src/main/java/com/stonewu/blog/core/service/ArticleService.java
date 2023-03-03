package com.stonewu.blog.core.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stonewu.blog.core.config.BlogCacheConfig;
import com.stonewu.blog.core.entity.Article;
import com.stonewu.blog.core.entity.Menber;
import com.stonewu.blog.core.entity.custom.ArticleWithMoreInfo;
import com.stonewu.blog.core.entity.custom.YearGroup;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stonewu
 * @since 2018-08-28
 */
public interface ArticleService extends IService<Article> {

    /**
     * 分页查询文章列表
     *
     * @param page
     * @param param
     * @return
     */
    Page<ArticleWithMoreInfo> selectArticleMoreByPage(Page<ArticleWithMoreInfo> page, ArticleWithMoreInfo param, Menber menber);

    /**
     * 按唯一标识查询文章
     *
     * @param identName
     * @return
     */
    ArticleWithMoreInfo selectArticleByIdentName(String identName, Menber menber);

    /**
     * 按创建时间倒序查询文章
     *
     * @return
     */
    List<ArticleWithMoreInfo> selectArticleOrderCreateTime();

    /**
     * 查询最近回复的文章
     *
     * @return
     */
    List<ArticleWithMoreInfo> selectArticleOrderReplyTime();

    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'selectGroupByCreateTime'")
    List<YearGroup> selectGroupByCreateTime();

    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'selectArticleByTagName_'+#tagName+'_'+#menber?.id+'_page_'+#page.current+'_'+#page.size")
    Page<ArticleWithMoreInfo> selectArticleByTagName(Page<ArticleWithMoreInfo> page, String tagName, Menber menberInfo);
}
