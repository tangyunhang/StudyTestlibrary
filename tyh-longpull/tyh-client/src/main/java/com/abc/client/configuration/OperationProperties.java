package com.abc.client.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 属性封装类
 */
@Data
@Component
@ConfigurationProperties(prefix = OperationProperties.CUSTOM_PREFIX)
public class OperationProperties {
    public static final String CUSTOM_PREFIX = "spring.custom";

    private String operator;

}
