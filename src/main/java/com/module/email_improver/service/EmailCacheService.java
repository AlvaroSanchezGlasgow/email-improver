package com.module.email_improver.service;


import com.module.email_improver.model.ImprovedEmail;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailCacheService {
    private static final String EMAIL_HISTORY_KEY = "email:history";
    private static final int MAX_HISTORY_SIZE = 5;

    private final RedisTemplate<String, ImprovedEmail> redisTemplate;

    public EmailCacheService(RedisTemplate<String, ImprovedEmail> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addToHistory(ImprovedEmail email) {
        redisTemplate.opsForList().leftPush(EMAIL_HISTORY_KEY, email);
        // Mantener solo los últimos 5 emails
        redisTemplate.opsForList().trim(EMAIL_HISTORY_KEY, 0, MAX_HISTORY_SIZE - 1);
    }

    public List<ImprovedEmail> getHistory() {
        return redisTemplate.opsForList().range(EMAIL_HISTORY_KEY, 0, MAX_HISTORY_SIZE - 1);
    }
}
