package com.stonewu.blog.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stonewu.blog.core.config.BlogCacheConfig;
import com.stonewu.blog.core.entity.Category;
import org.springframework.cache.annotation.Cacheable;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stonewu
 * @since 2018-08-28
 */
public interface CategoryService extends IService<Category> {

    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'selectArticleByIdentName'+#identName")
    Category selectByIdentName(String identName);
}
