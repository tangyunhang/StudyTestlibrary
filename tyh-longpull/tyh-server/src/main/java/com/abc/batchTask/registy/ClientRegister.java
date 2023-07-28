package com.abc.batchTask.registy;

import com.abc.InstanceInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: ClientRegister 客户端注册
 * @Author: 青衣醉
 * @Date: 2022/9/1 9:50 上午
 */
@Component
public class ClientRegister {
    //客户端注册信息
    ConcurrentHashMap registerMap = new ConcurrentHashMap<String, Set<InstanceInfo>>();
    protected void register(String serviceName, HashMap params, boolean isCopy){
        if (!registerMap.containsKey (serviceName)){
            InstanceInfo instanceInfo = InstanceInfo.builder ()
                    .ip ((String) params.get ("ip"))
                    .port ((String) params.get ("prot"))
                    .serviceName ((String) params.get ("serviceName"))
                    .build ();
            HashSet<InstanceInfo> instanceInfos = new HashSet<> ();
            instanceInfos.add (instanceInfo);
            registerMap.put (serviceName,instanceInfos);
        }

        //集群通知其他机器
        if (!isCopy){
            copyOtherRegister(serviceName,params,true);
        }
    }

    private void copyOtherRegister(String serviceName, HashMap params, boolean isCopy) {
    }
}
