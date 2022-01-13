package com.itzyq.accesslimit.controller;

import com.itzyq.accesslimit.anno.AccessLimit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: TestController
 * @description: TODO
 * @author: zyq
 * @date: 2021/2/18 14:30
 * @Version: 1.0
 */
@RestController
@RequestMapping("test")
public class TestController {

    @AccessLimit(seconds=30, maxCount=3, needLogin=false)
    @PostMapping("/fangshua")
    public String fangshua(){
        return "成功";
    }


}
