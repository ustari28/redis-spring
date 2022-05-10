package com.alan.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.UUID;

/**
 * @author Alan Dávila<br>
 * e-mail ustargab@gmail.com<br>
 * 28 dic. 2016 19:27
 */
@RestController
@Slf4j
public class RedisController {

    @Autowired
    ReactiveRedisOperations<String, BusinessData> businessDataTemplate;

    @Bean
    RouterFunction<ServerResponse> publish() {
        return RouterFunctions.route(RequestPredicates.GET("/publish/{text}"),
                req -> {
                    String idTran = UUID.randomUUID().toString();
                    Long inTs = System.currentTimeMillis();
                    BusinessData bd = new BusinessData(idTran, req.pathVariable("text"), inTs);
                    log.info("Sending object");
                    return ServerResponse.ok().body(
                            businessDataTemplate.convertAndSend("testtopic", bd)
                            , BusinessData.class);
                }
        );
    }

}
