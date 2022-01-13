package com.itzyq.accesslimit.anno;

import java.lang.annotation.*;

/**
 * @className: AccessLimit
 * @description: 自定义限制注解
 * @author: zyq
 * @date: 2021/2/18 13:59
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AccessLimit {

    int seconds();
    int maxCount();
    boolean needLogin() default true;
}
