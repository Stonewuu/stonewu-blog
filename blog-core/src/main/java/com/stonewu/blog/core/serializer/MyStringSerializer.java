package com.stonewu.blog.core.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MyStringSerializer implements RedisSerializer<String> {

    private final Charset charset;

    private String prefix;

    /**
     * Creates a new {@link MyStringSerializer} using {@link StandardCharsets#UTF_8
     * UTF-8}.
     */
    public MyStringSerializer(String prefix) {
        this(prefix, StandardCharsets.UTF_8);
    }

    /**
     * Creates a new {@link MyStringSerializer} using the given {@link Charset} to
     * encode and decode strings.
     *
     * @param charset must not be {@literal null}.
     */
    public MyStringSerializer(String prefix, Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.prefix = prefix;
        this.charset = charset;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.data.redis.serializer.RedisSerializer#deserialize(byte[])
     */
    @Override
    public String deserialize(@Nullable byte[] bytes) {
        return (bytes == null ? null : prefix + ":" + new String(bytes, charset));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.data.redis.serializer.RedisSerializer#serialize(java.lang
     * .Object)
     */
    @Override
    public byte[] serialize(@Nullable String string) {
        string = prefix + ":" + string;
        return (string == null ? null : string.getBytes(charset));
    }


}
