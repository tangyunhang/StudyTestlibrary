package com.abc.util;


import java.util.*;

/**
 * @Description: MD5Util
 * @Author: 青衣醉
 * @Date: 2022/9/5 5:01 下午
 */
public class MD5Util {

    //返回变动参数名
    public static List<String> compareUptoConfigList(Map<String,String> configData, Map<String,String> clientConfigData) {
        Objects.requireNonNull (configData);
        Objects.requireNonNull (clientConfigData);
        List<String> changedGroupKeys = new ArrayList<String> ();

        //遍历客户端配置，比较是否有变动
        for (Map.Entry<String,String> client : clientConfigData.entrySet ()) {
            String value = client.getValue ();
            String key = client.getKey ();
            if (!configData.containsKey (key)|| !configData.get (key).equals (value)) {
                changedGroupKeys.add (key+":"+value);
            }
        }

        return changedGroupKeys;
    }

    public static Map<String, String> getClientMD5Map(String probeModify) {
        Map<String,String> resultMap = new HashMap<> (5);
        if (null == probeModify || "".equals (probeModify)) {
            return resultMap;
        }
        String[] split = probeModify.split (",");
        for (int i = 0; i < split.length; i++) {
            String[] s = split[i].split (":");
            resultMap.put (s[0],s[1]);
        }
        return resultMap;
    }

    public static String compareMd5ResultString(List<String> changedGroups) {
        if (null == changedGroups) {
            return "";
        }
        StringBuilder sb = new StringBuilder();


        return sb.toString ();
    }
}
