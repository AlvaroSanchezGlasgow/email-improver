package com.module.email_improver.configuration;
import com.module.email_improver.model.ImprovedEmail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, ImprovedEmail> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ImprovedEmail> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Crear ObjectMapper con soporte para Java 8 Date/Time
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Configurar serializadores
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));

        template.setEnableDefaultSerializer(false);
        template.afterPropertiesSet();

        return template;
    }
}