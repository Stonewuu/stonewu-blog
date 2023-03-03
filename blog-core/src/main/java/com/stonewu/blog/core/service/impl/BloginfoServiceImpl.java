package com.stonewu.blog.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.stonewu.blog.core.config.BlogCacheConfig;
import com.stonewu.blog.core.entity.Bloginfo;
import com.stonewu.blog.core.mapper.BloginfoMapper;
import com.stonewu.blog.core.service.BaseServiceImpl;
import com.stonewu.blog.core.service.BloginfoService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stonewu
 * @since 2018-08-28
 */
@Service
public class BloginfoServiceImpl extends BaseServiceImpl<BloginfoMapper, Bloginfo> implements BloginfoService {

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'getActiveInfo'")
    public Bloginfo getActiveInfo() {
        return this.getOne(Wrappers.<Bloginfo>lambdaQuery().eq(Bloginfo::getActive, 1));
    }
}
