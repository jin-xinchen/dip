package com.itcheetah.securityjwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: SecurityJwtApplication
 * @description: 启动类
 * @author: cheetah
 * @date: 2021/6/29 15:59
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan({"com.itcheetah.securityjwt.mapper"})
public class SecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityJwtApplication.class, args);
    }
}
