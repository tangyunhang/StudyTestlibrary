package com.abc.listener.dispatcher;


import com.abc.listener.event.Event;
import com.abc.listener.listener.TyhEventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: EventDispatcher
 * @Author: 青衣醉
 * @Date: 2022/9/2 4:34 下午
 */

@Component
public class EventDispatcher implements ApplicationContextAware {
    private ExecutorService executor = null;
    //事件与监听器组
    private static final Map<Class, Set<TyhEventListener>> listenerMap = new ConcurrentHashMap<Class, Set<TyhEventListener>>();

    public EventDispatcher(){
        this.executor = Executors.newSingleThreadExecutor(new ThreadFactory () {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "com.abc.listener.client.listener");
                thread.setDaemon(true);

                return thread;
            }
        });

        this.executor.execute(()-> System.out.println ("EventDispatcher is start"));
    }

    static public void addListener(TyhEventListener listener){
        Class aClass = listener.getEventType ();
        if (!listenerMap.containsKey (listener.getEventType ())) {
            listenerMap.put (aClass,new HashSet<> ());
        }
        listenerMap.get (aClass).add (listener);
    }

    static public void pushEvent(Event event){
        if (null == event) {
            throw new IllegalArgumentException ();
        }
        Optional.ofNullable (listenerMap.get (event.getClass ()))
                .ifPresent (listeners ->{
                    listeners.forEach (listener-> listener.onEvent (event));
                } );
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //根据接口类型返回相应的所有bean
        applicationContext.getBeansOfType(TyhEventListener.class)
                .values ()
                .forEach (EventDispatcher::addListener);

    }
}
