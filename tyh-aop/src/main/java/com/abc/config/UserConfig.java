package com.abc.config;


import com.abc.bean.Usermanager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @Description: UserConfig
 * @Author: 青衣醉
 * @Date: 2022/6/10 5:12 下午
 */
@Configuration
public class UserConfig {
    @Value("${user.name}")
    private String name;

    @Bean
    public Usermanager cerateManager(){
        return new Usermanager (name);
    }
}
