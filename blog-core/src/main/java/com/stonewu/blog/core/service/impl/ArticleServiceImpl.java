package com.stonewu.blog.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stonewu.blog.core.config.BlogCacheConfig;
import com.stonewu.blog.core.entity.Article;
import com.stonewu.blog.core.entity.Menber;
import com.stonewu.blog.core.entity.custom.ArticleWithMoreInfo;
import com.stonewu.blog.core.entity.custom.YearGroup;
import com.stonewu.blog.core.mapper.ArticleMapper;
import com.stonewu.blog.core.service.ArticleService;
import com.stonewu.blog.core.service.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stonewu
 * @since 2018-08-24
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    private ArticleMapper mapper;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'selectArticleMoreByPage_'+#param.title+'_'+#param.categoryId+'_'+#param.status+'_'+#param.year+'_'+#menber?.id+'_page_'+#page.current+'_'+#page.size")
    public Page<ArticleWithMoreInfo> selectArticleMoreByPage(Page<ArticleWithMoreInfo> page, ArticleWithMoreInfo param, Menber menber) {
//        LambdaQueryWrapper<ArticleWithMoreInfo> wrapper = new LambdaQueryWrapper<ArticleWithMoreInfo>();
//        wrapper.like(ArticleWithMoreInfo::getTitle, param.getTitle());
//        wrapper.eq(ArticleWithMoreInfo::getCategoryId, param.getCategoryId());
//        wrapper.eq(ArticleWithMoreInfo::getStatus, param.getStatus());
        QueryWrapper<ArticleWithMoreInfo> wrapper = new QueryWrapper<ArticleWithMoreInfo>();
        if (StringUtils.isNotBlank(param.getTitle())) {
            wrapper.like("a.title", param.getTitle());
        }
        if (param.getCategoryId() != null) {
            wrapper.eq("a.category_id", param.getCategoryId());
        }
        if (param.getYear() != null) {
            wrapper.ge("a.create_time", LocalDateTime.of(LocalDate.of(param.getYear(), Month.JANUARY, 1), LocalTime.MIN));
            wrapper.le("a.create_time", LocalDateTime.of(LocalDate.of(param.getYear() + 1, Month.JANUARY, 1), LocalTime.MIN));
        }
        if (param.getStatus() != null) {
            wrapper.eq("a.status", param.getStatus());
        }
        wrapper.groupBy("a.id", "a.update_time");
        wrapper.orderByDesc("a.update_time");
        List<ArticleWithMoreInfo> articleWithMoreInfos = mapper.selectArticleMoreWithReply(page, wrapper, menber);
        articleWithMoreInfos.forEach(articleWithMoreInfo -> {
            Long increment = redisTemplate.opsForValue().increment("article_" + articleWithMoreInfo.getId(), 0);
            articleWithMoreInfo.setViewNum(increment);
        });
        return page.setRecords(articleWithMoreInfos);
    }

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'selectArticleByIdentName_'+#identName+'_'+#menber?.id")
    public ArticleWithMoreInfo selectArticleByIdentName(String identName, Menber menber) {
        QueryWrapper<ArticleWithMoreInfo> wrapper = new QueryWrapper<ArticleWithMoreInfo>();
        wrapper.eq("a.ident_name", identName);
        wrapper.eq("a.status", 1);
        ArticleWithMoreInfo articleWithMoreInfo = mapper.selectArticleMoreWithReply(wrapper, menber);
        Long increment = redisTemplate.opsForValue().increment("article_" + articleWithMoreInfo.getId(), 0);
        articleWithMoreInfo.setViewNum(increment);
        return articleWithMoreInfo;
    }

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'selectArticleOrderCreateTime'")
    public List<ArticleWithMoreInfo> selectArticleOrderCreateTime() {
        Page<ArticleWithMoreInfo> page = new Page<>(1, 5);
        QueryWrapper<ArticleWithMoreInfo> wrapper = new QueryWrapper<ArticleWithMoreInfo>();
        wrapper.orderByDesc("a.create_time");
        wrapper.groupBy("a.id", "a.create_time");
        wrapper.eq("a.status", 1);
        return mapper.selectArticleMore(page, wrapper);
    }

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'selectArticleOrderReplyTime'")
    public List<ArticleWithMoreInfo> selectArticleOrderReplyTime() {
        Page<ArticleWithMoreInfo> page = new Page<>(1, 5);
        QueryWrapper<ArticleWithMoreInfo> wrapper = new QueryWrapper<ArticleWithMoreInfo>();
        return mapper.selectRecentReply(page, wrapper);
    }

    /**
     * 查询年份归档
     *
     * @return
     */
    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'selectGroupByCreateTime'")
    public List<YearGroup> selectGroupByCreateTime() {
        return mapper.selectGroupByCreateTime();
    }

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'selectArticleByTagName_'+#tagName+'_'+#menber?.id+'_page_'+#page.current+'_'+#page.size")
    public Page<ArticleWithMoreInfo> selectArticleByTagName(Page<ArticleWithMoreInfo> page, String tagName, Menber menberInfo) {
        List<ArticleWithMoreInfo> articleWithMoreInfos = mapper.selectByTagNamePage(page, tagName, menberInfo);
        articleWithMoreInfos.forEach(articleWithMoreInfo -> {
            Long increment = redisTemplate.opsForValue().increment("article_" + articleWithMoreInfo.getId(), 0);
            articleWithMoreInfo.setViewNum(increment);
        });
        return page.setRecords(articleWithMoreInfos);
    }

}
