package com.itcheetah.securityjwt.entity.model;

import lombok.Data;

import java.util.Date;

/**
 * @author cheetah
 * @description jwt加密的类
 * @date 2021/6/29 22:54
 **/
@Data
public class Payload<T>{
    private String id;
    private T userInfo;
    private Date expiration;
}
