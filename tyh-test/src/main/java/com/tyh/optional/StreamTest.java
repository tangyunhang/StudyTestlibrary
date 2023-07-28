package com.tyh.optional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Description: StreamTest
 * @Author: 青衣醉
 * @Date: 2023/7/17 15:54
 */
public class StreamTest {
    public static void main(String[] args) {
        List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>> ();
        List<HashMap<String,Object>> list2 = new ArrayList<HashMap<String,Object>> ();
        for (int i = 0; i < 10; i++) {
            HashMap<String,Object> hashMap=new HashMap<String,Object>();
            hashMap.put ("key"+i,"value"+i);
            list.add (hashMap);
            HashMap<String,Object> hashMap2=new HashMap<String,Object>();
            hashMap.put ("key2"+i,"value2"+i);
            list2.add (hashMap2);
        }

        System.out.println (Stream.of (list,list2));
    }
}
