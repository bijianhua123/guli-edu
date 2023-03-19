package com.bijianhua.guli.common.base.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * redis生成全局唯一Id
 */
@Component
public class RedisIdWorker {

    /**
     * 初始时间为2010年1月1日1分1秒的时间戳
     */
    private static final long BEGIN_TIMESTAMP = 1262307661L;

    private StringRedisTemplate stringRedisTemplate;

    public RedisIdWorker(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public long nextId(String keyPrefix) {
        //1、生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long l = now.toEpochSecond(ZoneOffset.UTC);
        long timeStamp = l - BEGIN_TIMESTAMP;
        //2、生成序列号
        //2.1获取当天日期
        String format = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        //2.2自增长
        long increment = stringRedisTemplate.opsForValue().increment("inc:" + keyPrefix + ":" + format);
        return timeStamp << 32 | increment;
    }


}
