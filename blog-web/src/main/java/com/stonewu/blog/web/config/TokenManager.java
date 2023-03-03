package com.stonewu.blog.web.config;

import com.stonewu.blog.core.entity.SysUser;
import com.stonewu.blog.core.entity.auth.AdminUserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class TokenManager {

    @Value("${token.prefix}")
    private String tokenPrefix;

    private static final String SERIES_CACHE_PREFIX = ":login_tokens:";

    private static final String USERNAME_CACHE_PREFIX = ":login_username:";

    /**
     * 默认缓存时间
     */
    private static final int CACHED_TIME = 32;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public synchronized void createNewToken(AdminUserLoginToken token) {
        AdminUserLoginToken current = (AdminUserLoginToken) redisTemplate.opsForValue().get(tokenPrefix + SERIES_CACHE_PREFIX + token.getSeries());

        if (current != null) {
            throw new DataIntegrityViolationException("用户序列号： '" + token.getSeries() + "' 已经存在！");
        }

        current = token;
        redisTemplate.opsForValue().set(tokenPrefix + SERIES_CACHE_PREFIX + token.getSeries(), current, CACHED_TIME, TimeUnit.DAYS);
        // 增加userName和序列号映射
        redisTemplate.opsForSet().add(tokenPrefix + USERNAME_CACHE_PREFIX + token.getUsername(), token.getSeries());
        // 更新失效时间
        redisTemplate.expire(tokenPrefix + USERNAME_CACHE_PREFIX + token.getUsername(), CACHED_TIME, TimeUnit.DAYS);
    }

    public synchronized void updateToken(String series, SysUser user, String host) {
        // 从缓存中获取token
        AdminUserLoginToken token = getTokenForSeries(series);
        if (token == null) {
            throw new DataIntegrityViolationException("更新用户序列号： '" + series + "' 异常！ 该序列号不存在！");
        }
        // 转换为可序列化的token
        AdminUserLoginToken newToken = new AdminUserLoginToken(token.getUsername(), series, user, new Date(), host);

        // 更新token信息
        redisTemplate.opsForValue().set(tokenPrefix + SERIES_CACHE_PREFIX + series, newToken, CACHED_TIME, TimeUnit.DAYS);
        // 更新userName和序列号映射
        redisTemplate.opsForSet().add(tokenPrefix + USERNAME_CACHE_PREFIX + token.getUsername(), token.getSeries());
    }

    public synchronized AdminUserLoginToken getTokenForSeries(String seriesId) {
        AdminUserLoginToken token = (AdminUserLoginToken) redisTemplate.opsForValue().get(tokenPrefix + SERIES_CACHE_PREFIX + seriesId);
        // 转换为接口需要的token
        if (token == null) {
            return null;
        }
        return token;
    }

    public synchronized void removeUserTokens(String username) {
        Set<Object> seriesIds = (Set<Object>) redisTemplate.opsForSet().members(tokenPrefix + USERNAME_CACHE_PREFIX + username);
        // 删除用户和序列的关联ID
        redisTemplate.delete(tokenPrefix + USERNAME_CACHE_PREFIX + username);
        if (seriesIds == null || seriesIds.isEmpty()) {
            return;
        }
        // 从缓存中删除该记住账号信息
        seriesIds = seriesIds.stream().map(id -> tokenPrefix + USERNAME_CACHE_PREFIX + id).collect(Collectors.toSet());
        redisTemplate.delete(seriesIds);

    }
}
