package com.stonewu.blog.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.stonewu.blog.core.config.BlogCacheConfig;
import com.stonewu.blog.core.entity.Menber;
import com.stonewu.blog.core.mapper.MenberMapper;
import com.stonewu.blog.core.service.BaseServiceImpl;
import com.stonewu.blog.core.service.MenberService;
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
public class MenberServiceImpl extends BaseServiceImpl<MenberMapper, Menber> implements MenberService {

    @Override
    @Cacheable(cacheResolver = BlogCacheConfig.CACHE_RESOLVER_NAME, key = "'getMenberByNameEmail_'+#nickName+'_'+#email")
    public Menber getMenberByNameEmail(String nickName, String email){
        Menber menber = this.getOne(Wrappers.<Menber>lambdaQuery().eq(Menber::getNickName, nickName).eq(Menber::getEmail, email));
        return menber;
    }
}
