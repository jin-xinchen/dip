package com.itzyq.redislikes.config;

import lombok.Getter;

/**
 * @className: LikedStatusEnum
 * @description:点赞枚举
 * @author: zyq
 * @date: 2021/2/25 14:02
 * @Version: 1.0
 */
@Getter
public enum LikedStatusEnum {
    LIKE(1,"点赞"),
    UNLIKE(0,"取消点赞/未点赞"),
    ;

    private Integer code;
    private String msg;

    LikedStatusEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
