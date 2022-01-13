package com.itcheetah.securityjwt.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: ProductInfo
 * @Description: 商品实体类
 * @Date: 2020/9/27
 * @Author: cheetah
 * @Version: 1.0
 */
@Data
@TableName(value="product_info")
public class ProductInfo {

    @TableId(value="id")
    private int id;
    private String name;
    private Double price;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
}
