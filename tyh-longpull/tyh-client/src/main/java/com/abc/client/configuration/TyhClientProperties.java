package com.abc.client.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: TyhProperties
 * @Author: 青衣醉
 * @Date: 2022/8/31 4:10 下午
 */
@Data
@Component
@ConfigurationProperties(prefix = TyhClientProperties.PREFIX)
public class TyhClientProperties {
    public static final String PREFIX = "tyh";

    public String socketUrl;

    public String clientName;

    public String longPullingTimeout;

}


