package com.stonewu.blog.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.stonewu.blog.core.config.BlogCacheConfig;
import com.stonewu.blog.core.entity.Category;
import com.stonewu.blog.core.mapper.CategoryMapper;
import com.stonewu.blog.core.service.BaseServiceImpl;
import com.stonewu.blog.core.service.CategoryService;
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
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'selectByIdentName_'+#identName")
    public Category selectByIdentName(String identName) {
        return this.getOne(Wrappers.<Category>lambdaQuery().eq(Category::getIdentName, identName));
    }
}
