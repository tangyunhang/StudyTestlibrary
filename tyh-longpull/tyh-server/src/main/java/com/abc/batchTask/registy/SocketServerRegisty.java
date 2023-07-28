package com.abc.batchTask.registy;

import com.abc.batchTask.LongPollingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: SocketServerRegisty
 * @Author: 青衣醉
 * @Date: 2022/8/31 3:26 下午
 */
@Configuration
public class SocketServerRegisty {
    @Bean
    @ConditionalOnMissingBean
    public LongPollingService SocketServer(){
        return new LongPollingService ();
    }
}
