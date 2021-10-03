package com.alan.example;

import io.lettuce.core.ClientOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@SpringBootApplication
public class RedidSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedidSpringApplication.class, args);
    }

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        LettuceClientConfiguration configuration = LettuceClientConfiguration.builder()
                .clientName("publish")
                .clientOptions(ClientOptions.builder()
                        .build()).build();

        return new LettuceConnectionFactory();
    }


    @Bean
    public ReactiveRedisOperations<String, BusinessData> businessDataTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisSerializer<BusinessData> valueSerializer = new Jackson2JsonRedisSerializer<>(BusinessData.class);
        RedisSerializationContext<String, BusinessData> serializationContext = RedisSerializationContext.<String, BusinessData>newSerializationContext(RedisSerializer.string())
                .value(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<String, BusinessData>(lettuceConnectionFactory, serializationContext);
    }


}
