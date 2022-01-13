package com.itcheetah.securityjwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itcheetah.securityjwt.entity.ProductInfo;

import java.util.List;

public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

    List<ProductInfo> getProductInfoList();
}
