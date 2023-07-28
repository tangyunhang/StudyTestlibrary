package com.abc.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: ConfigCache缓存客户端配置信息配置
 * @Author: 青衣醉
 * @Date: 2022/9/22 4:33 下午
 */
@Component
public class ConfigCache {
    //缓存客户端配置信息配置
    public static ConcurrentHashMap<String, Map<String,String>> configCashMap =new ConcurrentHashMap<String, Map<String,String>>();

    public static Map<String, String> getConfigCasheMapByAppName(String appName){
        if (StringUtils.isEmpty (appName)) {
            return new HashMap<String, String>();
        }
        return configCashMap.get (appName);
    }

    public static ConcurrentHashMap<String, Map<String,String>> getConfigCashMap(){
        return configCashMap;
    }

    public static void addConfig(String appName,Map<String,String> data){
        configCashMap.put (appName,data);
    }

    public static void deleteConfig(String appName){
        configCashMap.remove (appName);
    }

    public static boolean containsKey(String appName) {
        return configCashMap.containsKey (appName);
    }
}
