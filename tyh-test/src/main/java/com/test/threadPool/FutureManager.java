package com.test.threadPool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description: FutureManager
 * @Author: 青衣醉
 * @Date: 2023/2/6 2:07 下午
 */
@Slf4j
public class FutureManager {
    ExecutorService executorService = Executors.newFixedThreadPool (1);

    public String execute() throws ExecutionException, InterruptedException {
        Future<String> submit = executorService.submit (new Callable<String> () {
            @Override
            public String call() throws Exception {
                Thread.sleep (3000);
                System.out.println ("33");
                return "33";
            }
        });
        return submit.get ();
    }

    @SneakyThrows
    public static void main(String[] args) {
        FutureManager futureManager = new FutureManager ();
        System.out.println (futureManager.execute ());

    }
}
