package com.batch.batchTask;

import lombok.Data;

import java.util.function.Consumer;

/**
 * @Description: Task
 * @Author: 青衣醉
 * @Date: 2023/3/23 4:47 下午
 */
@Data
public class Task<T>{
    long execTime;
    String code;
    String desc;
    T param;
    Consumer<T> consumer;
    public Task(Long execTime,String code,String desc,T param,Consumer<T> consumer){
        this.execTime=execTime;
        this.consumer =consumer;
        this.param =param;
        this.code = code;
        this.desc = desc;
    }

}
