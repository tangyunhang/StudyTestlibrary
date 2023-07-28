package com.test.threadPool;

import lombok.extern.slf4j.Slf4j;
import org.junit.rules.TestRule;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.*;

/**
 * @Description: Test
 * @Author: 青衣醉
 * @Date: 2022/8/5 4:34 下午
 */
@SpringBootTest
@Slf4j
public class Test {

    public void print() {
        String name = Thread.currentThread().getName();
        Thread.State state = Thread.currentThread ().getState ();
        try {
            log.info (name+"开始处理"+"state:"+state);
            TimeUnit.MILLISECONDS.sleep(500);
            log.info (name+"处理结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @org.junit.Test
    public void Test_0(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor (2, 2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable> ());

        threadPoolExecutor.execute (()->{
            print();

        });
            threadPoolExecutor.submit (()->{
                print();
            });

        threadPoolExecutor.submit (()->{
            System.out.println ("ddddd"+Thread.currentThread ().getName ());
            System.out.println ("阻塞任务个数："+threadPoolExecutor.getQueue ().size ());
            print();
;        });
        extracted(threadPoolExecutor);
        System.out.println ("阻塞任务个数："+threadPoolExecutor.getQueue ().size ());

        closeThread(threadPoolExecutor);
    }

    private void closeThread(ThreadPoolExecutor threadPoolExecutor){
        if(threadPoolExecutor.getQueue ().size ()==0){
            threadPoolExecutor.isShutdown ();
            // main线程被阻塞在了这里
            String name = Thread.currentThread().getName();
            Thread.State state = Thread.currentThread ().getState ();
            log.info (name+"结束阻塞,"+"state:"+state);
        }
    }
    private void extracted(Executor pool) {
        synchronized (pool) {
            try {
                // main线程被阻塞在了这里
                String name = Thread.currentThread().getName();
                Thread.State state = Thread.currentThread ().getState ();
                log.info (name+"开始阻塞,"+"state:"+state);
                pool.wait();
                log.info (name+"结束阻塞,"+"state:"+state);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @org.junit.Test
    public void test3(){
        Double[] dd ={0d};
        BigDecimal b = BigDecimal.valueOf (43332321321367554512213213132.16565464565);
        Double d1 = b.doubleValue ();
        dd[0]+=d1;
        Double d2 = 43332321321367554512213213132.16565464565;
        System.out.println (dd[0]);
        System.out.println (d1);
        System.out.println (dd[0].equals (d2));

    }

}
