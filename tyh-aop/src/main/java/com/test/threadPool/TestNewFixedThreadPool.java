package com.test.threadPool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
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

    @SneakyThrows
    @org.junit.Test
    public void test_(){
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        final CountDownLatch countDownLatch = new CountDownLatch (3);
        long startTime1 =System.currentTimeMillis ();
        pool.execute (()->{
            System.out.println ("1");
            print2();
            print ();
            countDownLatch.countDown ();
        });
        long startTime2 =System.currentTimeMillis ();
        System.out.println ("no.1="+(startTime2-startTime1));
        pool.execute (()->{
            System.out.println ("2");
            print2();
            print ();
            countDownLatch.countDown ();

        });
        long startTime3 =System.currentTimeMillis ();
        System.out.println ("no.2="+(startTime3-startTime2));
        pool.submit (() -> {
            System.out.println ("阻塞线程数2："+pool.getQueue ().size ());
            print2();
            print ();
            countDownLatch.countDown ();
        });

        System.out.println ("阻塞线程数1："+pool.getQueue ().size ());
        countDownLatch.await ();
        //等线程池内任务完全结束后，关闭线程池
        // pool.shutdown ();
        // while (true){
        //     if (pool.isTerminated ()) {
        //         break;
        //     }
        // }
        System.out.println ("线程活跃任务"+pool.getActiveCount ());

        log.info (Thread.currentThread().getName()+"结束阻塞");

        //extracted (pool);
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
