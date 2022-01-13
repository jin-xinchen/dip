package com.itzyq.accesslimit.interceptor;

import com.alibaba.fastjson.JSON;
import com.itzyq.accesslimit.anno.AccessLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @className: FangshuaInterceptor
 * @description: 防刷拦截器
 * @author: zyq
 * @date: 2021/2/18 14:02
 * @Version: 1.0
 */
@Component
public class FangshuaInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断请求是否属于方法的请求
        if(handler instanceof HandlerMethod){

            HandlerMethod hm = (HandlerMethod) handler;

            //获取方法中的注解,看是否有该注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null){
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean login = accessLimit.needLogin();
            String key = request.getRequestURI();
            //如果需要登录
            if(login){
                //获取登录的session进行判断
                //.....
                key+=""+"1";  //这里假设用户是1,项目中是动态获取的userId
            }

            //从redis中获取用户访问的次数
            /*AccessKey ak = AccessKey.withExpire(seconds);
            Integer count = redisService.get(ak,key,Integer.class);
            if(count == null){
                //第一次访问
                redisService.set(ak,key,1);
            }else if(count < maxCount){
                //加1
                redisService.incr(ak,key);
            }else{
                //超出访问次数
                render(response,CodeMsg.ACCESS_LIMIT_REACHED); //这里的CodeMsg是一个返回参数
                return false;
            }*/
            Integer count;
            if(Objects.isNull(redisTemplate.opsForValue().get(key))){
                count = 0;
            }else{
                count = (Integer) redisTemplate.opsForValue().get(key);
            }
            if(count == 0){
                redisTemplate.opsForValue().set(key,1,seconds, TimeUnit.SECONDS);
            }else if(count<maxCount){
                redisTemplate.opsForValue().increment(key);
            }else{
                //超出访问次数
                Map<String,Object> errMap=new HashMap<>();
                errMap.put("code",400);
                errMap.put("msg","请求超时，请稍后再试");
                render(response,errMap); //这里的CodeMsg是一个返回参数
                return false;
            }
        }
        return true;
    }


    private void render(HttpServletResponse response, Map<String,Object> errMap) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(errMap);
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
