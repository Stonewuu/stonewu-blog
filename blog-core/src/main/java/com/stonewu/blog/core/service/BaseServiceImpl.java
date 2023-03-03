package com.stonewu.blog.core.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stonewu.blog.core.config.BlogCacheConfig;
import org.apache.ibatis.session.SqlSession;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 对原生的数据库错误进行封装
 *
 * @param <M>
 * @param <T>
 * @author phl
 */
@Service
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IService<T> {

    @Override
    public M getBaseMapper() {
        return super.getBaseMapper();
    }

    @Override
    protected boolean retBool(Integer result) {
        return super.retBool(result);
    }

    @Override
    protected Class<T> currentModelClass() {
        return super.currentModelClass();
    }

    @Override
    protected SqlSession sqlSessionBatch() {
        return super.sqlSessionBatch();
    }

    @Override
    protected void closeSqlSession(SqlSession sqlSession) {
        super.closeSqlSession(sqlSession);
    }

    @Override
    protected String sqlStatement(SqlMethod sqlMethod) {
        return super.sqlStatement(sqlMethod);
    }

    @Override
    @CacheEvict(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, allEntries = true)
    public boolean save(T entity) {
        return super.save(entity);
    }

    @Override
    @CacheEvict(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, allEntries = true)
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        return super.saveBatch(entityList, batchSize);
    }

    @Override
    @CacheEvict(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, allEntries = true)
    public boolean saveOrUpdate(T entity) {
        return super.saveOrUpdate(entity);
    }

    @Override
    @CacheEvict(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, allEntries = true)
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        return super.saveOrUpdateBatch(entityList, batchSize);
    }

    @Override
    @CacheEvict(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, allEntries = true)
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @CacheEvict(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, allEntries = true)
    public boolean removeByMap(Map<String, Object> columnMap) {
        return super.removeByMap(columnMap);
    }

    @Override
    @CacheEvict(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, allEntries = true)
    public boolean remove(Wrapper<T> wrapper) {
        return super.remove(wrapper);
    }

    @Override
    @CacheEvict(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, allEntries = true)
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @Override
    @CacheEvict(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, allEntries = true)
    public boolean updateById(T entity) {
        return super.updateById(entity);
    }

    @Override
    @CacheEvict(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, allEntries = true)
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        return super.update(entity, updateWrapper);
    }

    @Override
    @CacheEvict(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, allEntries = true)
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        return super.updateBatchById(entityList, batchSize);
    }

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'getById_'+#id")
    public T getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        return super.listByIds(idList);
    }

    @Override
    public Collection<T> listByMap(Map<String, Object> columnMap) {
        return super.listByMap(columnMap);
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        return super.getOne(queryWrapper, throwEx);
    }

    @Override
    public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
        return super.getMap(queryWrapper);
    }

    @Override
    public int count(Wrapper<T> queryWrapper) {
        return super.count(queryWrapper);
    }

    @Override
    public List<T> list(Wrapper<T> queryWrapper) {
        return super.list(queryWrapper);
    }

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'listAll'", unless="#result == null")
    public List<T> list() {
        return super.list();
    }

    @Override
    public IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper) {
        return super.page(page, queryWrapper);
    }

    @Override
    public List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
        return super.listMaps(queryWrapper);
    }

    @Override
    public <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return super.listObjs(queryWrapper, mapper);
    }

    @Override
    public IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper) {
        return super.pageMaps(page, queryWrapper);
    }

    @Override
    public <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return super.getObj(queryWrapper, mapper);
    }
}
