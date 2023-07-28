package com.abc.common.http.param;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: Header
 * @Author: 青衣醉
 * @Date: 2022/9/27 9:09 上午
 */
public class Header {
    public static final Header EMPTY = newInstance();
    private final Map<String, String> header = new LinkedHashMap ();
    private final Map<String, List<String>> originalResponseHeader = new LinkedHashMap();

    private Header() {
        this.addParam("Content-Type", "application/json;charset=UTF-8");
        this.addParam("Accept-Charset", "UTF-8");
        this.addParam("Accept-Encoding", "gzip");
    }

    public static Header newInstance() {
        return new Header ();
    }

    public void addParam(String key,String value){
        if (!StringUtils.isEmpty (key)){
            this.header.put (key,value);
        }
    }
}
