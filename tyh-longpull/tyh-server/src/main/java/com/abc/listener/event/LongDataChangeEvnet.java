package com.abc.listener.event;

import com.abc.InstanceInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description: LongDataChangeEvnet, 数据变更事件
 * @Author: 青衣醉
 * @Date: 2022/9/2 3:02 下午
 */
@Data
public class LongDataChangeEvnet implements Event {
    public final String groupKey;


    public final Map<String,String> clientConfigData;

    public LongDataChangeEvnet(String groupKey, Map<String,String> clientConfigData) {
        this.groupKey = groupKey;
        this.clientConfigData =clientConfigData;
    }
}
