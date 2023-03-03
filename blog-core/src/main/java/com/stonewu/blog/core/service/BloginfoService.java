package com.stonewu.blog.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stonewu.blog.core.config.BlogCacheConfig;
import com.stonewu.blog.core.entity.Bloginfo;
import org.springframework.cache.annotation.Cacheable;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stonewu
 * @since 2018-08-28
 */
public interface BloginfoService extends IService<Bloginfo> {

    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'getActiveInfo'")
    Bloginfo getActiveInfo();
}
