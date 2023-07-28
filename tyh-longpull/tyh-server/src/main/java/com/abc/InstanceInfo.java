package com.abc;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: InstanceInfo
 * @Author: 青衣醉
 * @Date: 2022/8/31 5:22 下午
 */
@Data
@Builder
public class InstanceInfo {
    private String ip;
    private String port;
    private String serviceName;
}
