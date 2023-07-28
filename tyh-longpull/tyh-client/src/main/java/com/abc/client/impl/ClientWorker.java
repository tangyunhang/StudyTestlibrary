package com.abc.client.impl;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.common.lifecycle.Closeable;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * @Description: ClientWorker
 * @Author: 青衣醉
 * @Date: 2022/9/22 5:50 下午
 */
@Component
public class ClientWorker implements Closeable {
    final ScheduledExecutorService executor;

    final ScheduledExecutorService executorService;

    public ClientWorker() {
        this.executor = Executors.newScheduledThreadPool(1, new ThreadFactory () {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("com.abc.client.Worker." + agent.getName());
                t.setDaemon(true);
                return t;
            }
        });

        this.executorService = Executors
                .newScheduledThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setName("com.abc.client.Worker.longPolling." + agent.getName());
                        t.setDaemon(true);
                        return t;
                    }
                });
    }

    @Override
    public void shutdown() throws NacosException {
            executor.shutdown ();
    }
}
