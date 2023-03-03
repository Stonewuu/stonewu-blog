package com.stonewu.blog.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.stonewu.blog.core.config.BlogCacheConfig;
import com.stonewu.blog.core.entity.Blogsetting;
import com.stonewu.blog.core.mapper.BlogsettingMapper;
import com.stonewu.blog.core.service.BaseServiceImpl;
import com.stonewu.blog.core.service.BlogsettingService;
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
public class BlogsettingServiceImpl extends BaseServiceImpl<BlogsettingMapper, Blogsetting> implements BlogsettingService {

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'getActiveSetting'")
    public Blogsetting getActiveSetting() {
        return this.getOne(Wrappers.<Blogsetting>lambdaQuery().eq(Blogsetting::getActive, 1));
    }
}
