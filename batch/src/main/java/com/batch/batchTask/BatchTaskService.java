package com.batch.batchTask;


import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: 批量调度
 * 目标：将任务切分成N快执行，并将耗时topM单独存储，下次调用单独执行
 * @Author: 青衣醉
 * @Date: 2023/3/22 2:17 下午
 */
@Component
public class BatchTaskService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger (BatchTaskService.class);
    /**
     * 批量线程数
     */
     int batchSize = 4;
    /**
     * 高耗时任务数
     */
     int optimizationTaskSize;

    /**
     *是否启用高耗时优化任务
     */
    static boolean isEnableOptimization = true;
    /**
     * 高耗时队列
     */
    final Queue<OptimizationTask> qptTasks;

    final ExecutorService executorService;

    CountDownLatch countDownLatch;

    /**
     * 所有任务列表
     */
    List<Task> allSubs;

    public BatchTaskService(){
        this(true,4);
    }
    public BatchTaskService(boolean isEnableOptimization,int batchSize) {
        this.qptTasks = new ConcurrentLinkedQueue<OptimizationTask> ();
        this.setOptimization(isEnableOptimization);
        this.setBatchSize(batchSize);
        this.setOptimizationTaskSize();
        this.executorService = Executors
                .newFixedThreadPool (6, r-> {
                    Thread t = new Thread(r);
                    t.setName("批量任务调度：Thread-"+t.getId ());
                    t.setDaemon(true);
                    return t;
                });

    }
    /**
     * 设置优化任务数
     */
    public void setOptimizationTaskSize(){
        int size = batchSize * 2 / 3;
        this.optimizationTaskSize = size==0?1:size;
    }
    /**
     * 设置批量大小
     */
    public void setBatchSize(Integer batchSize){
        this.batchSize = batchSize>6?4:batchSize;
    }
    /**
     * 设置是否开启优化
     */
    public void setOptimization(boolean bol){
        this.isEnableOptimization = bol;
    }
    /**
     * 获取是否开启优化
     */
    public boolean  isEnableOptimization(){
        return this.isEnableOptimization;
    }
    /**
     * 执行任务
     */
    public  <T>boolean doBatchTask(List<Task> taskList){
        int threadCount = qptTasks.size ()>0?(batchSize+optimizationTaskSize):batchSize;
        countDownLatch = new CountDownLatch (threadCount);
        log.info ("countDownLatchCount:{}",countDownLatch.getCount ());
        // 已执行任务，根据执行时间排序
        allSubs = Collections.synchronizedList(taskList);

        if (this.isEnableOptimization()) {
            //高耗时任务处理优先执行，剩下任务拆分执行
            qptTasks.forEach (optimizationTask -> {
                executorService.execute (optimizationTask);

            });
        }
        //过滤高耗时任务
        List<Task> collect = taskList.stream ().filter (task -> qptTasks.stream ().noneMatch (optimizationTask ->
                optimizationTask.task.code.equals (task.getCode ()))).collect (Collectors.toList ());

        //普通任务拆分
        List<List<Task>> lists = ListUtils.splitList2 (collect, batchSize);

        //多线程执行处理普通任务
        Stream<BatchTask> batchTaskList = createBatchTaskList (lists);
            batchTaskList.forEach (batchTask -> {
            executorService.execute (batchTask);
        });

        try {
            countDownLatch.await ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        if (!this.isEnableOptimization ()) {
            return true;
        }
        //重新筛选高耗时任务，可请求返回异步执行
        executorService.execute (()->{
            //清空原有队列
            qptTasks.clear ();
            int numQpt = optimizationTaskSize;
            while (numQpt-->0){
                //qptTasks.offer (new OptimizationTask (allSubs.poll ()));
                Task poll = allSubs.get (numQpt);
                qptTasks.offer (new OptimizationTask (poll));
                log.info ("高耗时任务:{}",poll.getCode ());
            }
        });
        return true;
    }

    private <T> Stream<BatchTask> createBatchTaskList(List<List<Task>> lists) {

        return lists.stream ().map (list -> createBatchTask (list));
    }

    public <T>BatchTask createBatchTask(List<Task> lists){

        return new BatchTask (lists);
    }

    //批量任务
    class BatchTask implements Runnable{

        List<Task> taskList;

        public <T> BatchTask(List<Task> taskList){
            this.taskList = taskList;
        }

        @Override
        public void run() {
            taskList.forEach (task->{
                long startTime = System.currentTimeMillis ();
                task.consumer.accept (task.param);
                long endTime = System.currentTimeMillis ();
                task.setExecTime (endTime-startTime);
                log.info ("批量线程：{},任务：{},执行时间={}",Thread.currentThread ().getName (),task.code,endTime-startTime);
                allSubs.add (task);
            });
            countDownLatch.countDown ();
        }
    }

    //高耗时任务
    class OptimizationTask implements Runnable{
        Task task;
         public OptimizationTask(Task task){
             this.task = task;
         }
        @Override
        public void run(){
            long startTime = System.currentTimeMillis ();
            task.consumer.accept (task.param);
            long endTime = System.currentTimeMillis ();
            task.setExecTime (endTime-startTime);
            log.info ("高耗时线程：{},任务：{},执行时间={}",Thread.currentThread ().getName (),task.code,endTime-startTime);
            allSubs.add (task);
            countDownLatch.countDown ();
        }
    }

}
