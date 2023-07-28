package com.abc.service;



import com.abc.bean.ConfigEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public interface ConfigService {


    void addConfig(Map params);

    void deleteActiveConfig(String dataId);

    ConfigEntity findActiveConfig(String dataId);

    String doLongPollingConfig(HttpServletRequest request, HttpServletResponse response,Map<String, String> clientConfigMap);

}
