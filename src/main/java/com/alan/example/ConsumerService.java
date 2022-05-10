package com.alan.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class ConsumerService {
    @Autowired
    ReactiveRedisOperations<String, BusinessData> businessDataTemplate;

    @PostConstruct
    public void consumer() {
        businessDataTemplate.listenTo(ChannelTopic.of("testtopic"))
                .map(ReactiveSubscription.Message::getMessage)
                .subscribe(c -> {
                    log.info("message {}", c);
                });
    }
}
