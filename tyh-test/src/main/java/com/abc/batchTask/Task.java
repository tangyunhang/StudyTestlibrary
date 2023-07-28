package com.abc.batchTask;

import java.util.function.Consumer;

/**
 * @Description: Task
 * @Author: 青衣醉
 * @Date: 2023/3/23 4:47 下午
 */
public class Task{
    Long execTime;
    String desc;
    Object param;
    Consumer consumer;
    public Task(Long execTime,String desc,Object param,Consumer consumer){
        this.execTime=execTime;
        this.consumer =consumer;
        this.param =param;
        this.desc = desc;
    }

    public long getExecTime() {
        return execTime;
    }

    public void setExecTime(long execTime) {
        this.execTime = execTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

}
