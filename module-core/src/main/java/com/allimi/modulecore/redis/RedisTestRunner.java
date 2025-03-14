package com.allimi.modulecore.redis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTestRunner implements CommandLineRunner {

    private final StringRedisTemplate redisTemplate;

    public RedisTestRunner(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            redisTemplate.opsForValue().set("testKey", "Hello Redis!");
            String value = redisTemplate.opsForValue().get("testKey");
            System.out.println("✅ Redis 연결 성공! testKey 값: " + value);
        } catch (Exception e) {
            System.err.println("❌ Redis 연결 실패! " + e.getMessage());
        }
    }
}
