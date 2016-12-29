package com.alan.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;

@SpringBootApplication
public class RedidSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedidSpringApplication.class, args);
    }

    @Bean
    public ImportTargetListener importTargetListener() {
        return new ImportTargetListener();
    }
    @Value("${redis.topic}")
    private String topic;
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private Integer port;

    @Bean
    public MessageListenerAdapter listenerAdapter(ImportTargetListener importTargetListener) {
        MessageListenerAdapter messageListenerAdapter = new
                MessageListenerAdapter(importTargetListener);
        messageListenerAdapter
                .setSerializer(new Jackson2JsonRedisSerializer<>(ImportTarget.class));
        return messageListenerAdapter;
    }

    @Bean
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory con = new JedisConnectionFactory();
        con.setHostName("localhost");
        con.setPort(18840);
        con.setDatabase(2);
        return con;
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListenerAdapter, new PatternTopic(topic));
        return container;
    }

    @Bean
    public RedisTemplate<String, ImportTarget> importTargetCache(JedisConnectionFactory connectionFactory) {
        final RedisTemplate<String, ImportTarget> importTargetCache = new RedisTemplate<>();
        importTargetCache.setConnectionFactory(connectionFactory);
        importTargetCache.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(ImportTarget.class));
        return importTargetCache;
    }
}
