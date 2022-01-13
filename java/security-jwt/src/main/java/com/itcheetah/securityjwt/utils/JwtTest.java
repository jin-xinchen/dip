package com.itcheetah.securityjwt.utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @className: JwtTest
 * @description: TODO
 * @author: cheetah
 * @date: 2021/6/29 23:55
 * @Version: 1.0
 */
public class JwtTest {

    private String privateKey = "C:/Users/Desktop/jwt/id_key_rsa";

    private String publicKey = "C:/Users/Desktop/jwt/id_key_rsa.pub";

    @Test
    public void test1() throws Exception{
        RsaUtils.generateKey(publicKey,privateKey,"dpb",1024);
    }


    @Test
    public void test2(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123123"));

        //PasswordEncoder passwordEncoder = new PasswordEncoder() ;
    }

}
