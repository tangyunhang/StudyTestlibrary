package com.test.threadPool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * @Description: TestNewFixedThreadPool
 * @Author: 青衣醉
 * @Date: 2022/8/5 10:26 上午
 */
@SpringBootTest
@Slf4j
public class TestNewFixedThreadPool {
    @Autowired
    private ExecutorService executorService;
    // 静态方法：打印一个元素（黑色）休眠一次
    public void print() {
        String name = Thread.currentThread().getName();
        Thread.State state = Thread.currentThread ().getState ();
        try {
            log.info (name+"开始处理"+"state:"+state);
            TimeUnit.MILLISECONDS.sleep(5000);
            log.info (name+"处理结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void print2() {
        System.out.println ("ddddddddd");
    }

    @org.junit.Test
    public void test_(){
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        pool.execute (()->{
            System.out.println ("1");
            print2();
            print ();
        });
        pool.execute (()->{print ();
            print2();
            System.out.println ("2");
        });
        pool.submit (() -> {
            System.out.println ("阻塞线程数2："+pool.getQueue ().size ());
            print ();
        });
        System.out.println ("阻塞线程数1："+pool.getQueue ().size ());
        extracted (pool);
    }

    private void extracted(Executor pool) {
        synchronized (pool) {
            try {
                // main线程被阻塞在了这里
                String name = Thread.currentThread().getName();
                Thread thread = Thread.currentThread ();
                Thread.State state = Thread.currentThread ().getState ();
                log.info (name+"开始阻塞,"+"state:"+state);
                //thread.wait ();
                pool.wait();
                log.info (name+"结束阻塞,"+"state:"+state);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
