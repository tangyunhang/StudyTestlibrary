package com.abc.test;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description: AcquirePassword
 * @Author: 青衣醉
 * @Date: 2023/2/15 2:49 下午
 */
public class AcquirePassword {
    public static void main(String[] args) {
        String password="123456";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder ();
        String encode = bCryptPasswordEncoder.encode (password);
        System.out.println (encode);
    }
}
