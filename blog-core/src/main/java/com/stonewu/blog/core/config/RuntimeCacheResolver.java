package com.stonewu.blog.core.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

class RuntimeCacheResolver
        extends SimpleCacheResolver {

    protected RuntimeCacheResolver(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        Type[] typeParameters = ((ParameterizedType) context.getTarget().getClass().getGenericSuperclass()).getActualTypeArguments();
        String className = typeParameters[1].getTypeName();
        return Arrays.asList(className.substring(className.lastIndexOf(".") + 1, className.length()));
    }
}