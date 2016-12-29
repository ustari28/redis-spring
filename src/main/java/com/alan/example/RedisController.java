package com.alan.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author Alan Dávila<br>
 *         e-mail ustargab@gmail.com<br>
 *         28 dic. 2016 19:27
 */
@RestController
@RequestMapping("/redis/api/v1")
@Slf4j
public class RedisController {

    @Autowired
    private RedisTemplate<String, ImportTarget> importTargetTemplate;
    @Value("${redis.topic}")
    private String topic;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public ResponseEntity<?> sendRedis(){
        log.info("enter");
        final ImportTarget it = new ImportTarget();
        it.setServerid(new Random().nextLong());
        importTargetTemplate.convertAndSend(topic, it);
        log.info("response:" + it.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
