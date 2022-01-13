package com.itzyq.redislikes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.itzyq.redislikes.mapper")
public class RedisLikesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisLikesApplication.class, args);
    }

}
