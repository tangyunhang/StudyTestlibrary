package com.abc.client.controller;

import com.abc.batchTask.LongPollingService;
import com.abc.service.ConfigService;
import com.abc.util.MD5Util;
import com.alibaba.nacos.api.common.Constants;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: SocketController
 * @Author: 青衣醉
 * @Date: 2022/8/31 3:17 下午
 */
@RestController
public class ConfigController {

    @Autowired
    private ConfigService configService;
    @Autowired
    LongPollingService longPollingService;
    @SneakyThrows
    @PostMapping("/listener")
    @ResponseBody
    public void Listener (HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
        String probeModify = request.getParameter("Listening-Configs");
        if (StringUtils.isBlank(probeModify)) {
            throw new IllegalArgumentException("invalid probeModify");
        }

        probeModify = URLDecoder.decode(probeModify, Constants.ENCODE);

        Map<String, String> clientConfigMap = MD5Util.getClientMD5Map(probeModify);
        configService.doLongPollingConfig (request,response,clientConfigMap);
        // 返回固定的JSON串
        // JSONObject result = new JSONObject();
        // result.put("message", "登录失效");
        // result.put("code", 1000);
        // ServletOutputStream output = response.getOutputStream();
        // output.write(result.toString().getBytes());
        // output.flush();
    }

    @SneakyThrows
    @PostMapping("/addConfig")
    public void UpdateConfig(@RequestBody HashMap params){
        configService.addConfig (params);
        JSONObject result = new JSONObject();
    }

}
