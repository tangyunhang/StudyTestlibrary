package com.abc.service.impl;

import com.abc.bean.ConfigEntity;
import com.abc.listener.dispatcher.EventDispatcher;
import com.abc.listener.event.LongDataChangeEvnet;
import com.abc.repository.ConfigRepository;
import com.abc.batchTask.LongPollingService;
import com.abc.service.ConfigCache;
import com.abc.service.ConfigService;
import com.abc.util.LocalDateUtils;
import com.abc.util.MD5Util;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @Description: ConfigServiceImpl,配置信息处理逻辑类
 * @Author: 青衣醉
 * @Date: 2022/9/5 5:11 下午
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    ConfigRepository configRepository;

    @Autowired
    private LongPollingService longPollingService;

    @Transactional
    @Override
    public void addConfig(Map clientConfig) {
        Objects.requireNonNull (clientConfig);
        String groupKey = (String)clientConfig.get ("application-name");
        String dataId = groupKey+".yml";
        ConfigEntity build = ConfigEntity.builder ()
                .dataId (dataId)
                .groupkey (groupKey)
                .isActive (Boolean.TRUE)
                .build ();

        Example<ConfigEntity> examplee = Example.of (build);

        configRepository.findOne (examplee)
                    .ifPresent (configEntity -> {
                        Map<String,String> configData = JSONObject.parseObject (configEntity.getConfigInfo (), Map.class);
                        List<String> list = MD5Util.compareUptoConfigList (configData, clientConfig);
                        //比较配置信息是否修改
                        if(list.size ()== 0){
                            configRepository.deleteById (configEntity.getId ());
                        }else {
                            this.updateConfigActive (dataId);
                            //发布配置变更事件
                            EventDispatcher.pushEvent (new LongDataChangeEvnet (groupKey,clientConfig));
                        }
                        //更新客户端配置缓存
                        ConfigCache.addConfig (groupKey,configData);
                    });

        build.setConfigInfo (clientConfig.toString ());
        build.setVartionDate (LocalDateUtils.getNowDateStr ());
        build.setCreatedTime (LocalDateUtils.getNowDate ());
        //插入配置信息
        configRepository.save (build);
    }

    @Override
    @Transactional
    public void deleteActiveConfig(String dataId) {
        configRepository.deleteByDataIdAndIsActive(dataId,Boolean.TRUE);
    }

    @Override
    public ConfigEntity findActiveConfig(String dataId) {
        ConfigEntity activeConfig = configRepository.findByDataIdAndIsActive (dataId, Boolean.TRUE);
        return Optional.ofNullable (activeConfig)
                .orElseThrow (NullPointerException::new);
    }

    @Override
    public String doLongPollingConfig(HttpServletRequest request, HttpServletResponse response, Map<String, String> clientConfigMap) {
        if (LongPollingService.isSupportLongPolling (request)) {
            longPollingService.addLongPollingClient (request,response,clientConfigMap);
            return HttpServletResponse.SC_OK + "";
        }
        // Disable cache.
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache,no-store");
        response.setStatus(HttpServletResponse.SC_OK);
        return HttpServletResponse.SC_OK + "";
    }


    public void updateConfigActive(String dataId){
        int rows = configRepository.makeConfigIsActive (dataId);
        if (rows == 0) {
            throw new IllegalArgumentException("config Not Found: " + dataId);
        }
    }
}
