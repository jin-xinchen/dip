package com.itcheetah.securityjwt.controller;

import com.itcheetah.securityjwt.common.AjaxResult;
import com.itcheetah.securityjwt.entity.ProductInfo;
import com.itcheetah.securityjwt.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: ProductInfoController
 * @Description: 商品类
 * @Date: 2020/9/27
 * @Author: cheetah
 * @Version: 1.0
 */
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;

    @RequestMapping("/getProductList")
    public AjaxResult getProductList(){
        List<ProductInfo> list = productInfoService.getProductInfoList();
        return AjaxResult.success(list);
    }

}
