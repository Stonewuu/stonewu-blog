package com.stonewu.blog.core.config;

import com.stonewu.blog.core.serializer.MyStringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.Duration;

@Configuration
@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24 * 30, redisNamespace = "stonewu-blog:stonewu-web:session")//注解，开启redis集中session管理
public class BlogCacheConfig {

    public final static String CACHE_RESOLVER_NAME = "blogCacheResolver";

    @Value("${platform.name}")
    private String platformName;

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisTemplate<?, ?> redisTemplate) {
        MyStringSerializer redisObjectSerializer = new MyStringSerializer(platformName);
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setKeySerializer(redisObjectSerializer);
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setHashKeySerializer(redisObjectSerializer);
        redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        StringRedisSerializer redisObjectSerializer = new StringRedisSerializer();
        // 使用这个方式会导致 CacheEvict 无法工作
//        MyStringSerializer redisObjectSerializer = new MyStringSerializer(platformName);
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10))
                .computePrefixWith(cacheName -> platformName.concat(":").concat(cacheName).concat(":"))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisObjectSerializer))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializationRedisSerializer));
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cacheConfiguration).build();
    }

    @Bean(CACHE_RESOLVER_NAME)
    public CacheResolver cacheResolver(CacheManager cacheManager) {
        return new RuntimeCacheResolver(cacheManager);
    }
}
