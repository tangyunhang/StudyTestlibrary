package com.abc.batchTask;

import com.abc.listener.dispatcher.EventDispatcher;
import com.abc.listener.event.Event;
import com.abc.listener.event.LongDataChangeEvnet;
import com.abc.listener.listener.TyhEventListener;
import com.abc.service.ConfigCache;
import com.abc.util.MD5Util;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Description: LongPollingService
 * @Author: 青衣醉
 * @Date: 2022/8/31 3:21 下午
 */
@Component
@Slf4j
public class LongPollingService {
    private final static boolean IS_COPY = false;
    public static final String LONG_POLLING_HEADER = "Long-Pulling-Timeout";
    public static final String CLIENT_APPNAME_HEADER = "Client-AppName";
    public static final String CLIENT_PROPERTIES_HEADER = "Client-Properties";
    // 执行定时任务的时间间隔
    private int period = 5;

    // 当前正在执行的task合集
    private Map futures = new HashMap<>();

    private String taskPrefix ="DoSomething-Task";

    private ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newScheduledThreadPool (4);
    //缓存客户端配置信息配置
    // private ConcurrentHashMap<String,Map<String,String>>  configCashMap =new ConcurrentHashMap<String, Map<String,String>>();

    private ScheduledExecutorService service = Executors.newScheduledThreadPool(20,new NamedThreadFactory ("Schedule-Task"));
    private HttpServletRequest req;
    private HttpServletResponse response;
    /**
     * ClientLongPolling subscibers.长轮训队列
     */
    final Queue<ClientLongPolling> allSubs;

    public LongPollingService(){
        allSubs = new ConcurrentLinkedQueue<ClientLongPolling>();
        service.scheduleWithFixedDelay (new StatTask(), 0L, 10L, TimeUnit.SECONDS);
        //添加配置变更监听
        EventDispatcher.addListener (new TyhEventListener<LongDataChangeEvnet> () {
            @Override
            public void onEvent(LongDataChangeEvnet event) {
                log.info ("数据发生变化");
                if (event instanceof LongDataChangeEvnet) {
                    LongDataChangeEvnet evt = (LongDataChangeEvnet) event;
                    pool.execute (new DataChangeTask(evt.getGroupKey (),evt.getClientConfigData ()));
                }
            }

            @Override
            public Class<? extends Event> getEventType() {
                return LongDataChangeEvnet.class;
            }
        });
    }
    public static boolean isSupportLongPolling(HttpServletRequest req) {
        return null != req.getHeader(LONG_POLLING_HEADER);
    }
    public void addLongPollingClient(HttpServletRequest req, HttpServletResponse response,Map<String,String> clientConfigMap) {
        this.req = req;
        this.response = response;
        // 设置客户端的超时时间
        String timeoOut = req.getHeader (LongPollingService.LONG_POLLING_HEADER);
        String appName = req.getHeader (LongPollingService.CLIENT_APPNAME_HEADER);
        long start = System.currentTimeMillis();
        /*比较缓存中的配置数据，不存在或不同则直接返回*/
        List<String> changedGroups = MD5Util.compareUptoConfigList (ConfigCache.getConfigCasheMapByAppName (appName), clientConfigMap);
        if(changedGroups.size ()>0){
            generateResponse(req,response, changedGroups);
            log.info ("{}|{}|{}|{}",System.currentTimeMillis ()-start,appName,
                    req.getHeader (LongPollingService.CLIENT_APPNAME_HEADER)
                    ,"polling");
            return;
        }
        // 提前500ms返回响应，为避免客户端超时.
        String ip = null;
        long timeout = Math.max (10000, Long.parseLong (timeoOut) - 500);
        // 必须要由HTTP线程调用，否则离开后容器会立即发送响应.
        final AsyncContext asyncContext = req.startAsync ();
        // AsyncContext.setTimeout()超时时间不准确，所以只能自己控制.
        asyncContext.setTimeout (0L);
        // 把客户端的长轮询请求封装成ClientLongPolling交给pool执行。
        pool.execute (new ClientLongPolling (asyncContext, timeout,ip,clientConfigMap));
    }

    void generateResponse(HttpServletRequest request, HttpServletResponse response, List<String> changedGroups) {
        if (null == changedGroups) {
            return;
        }
        try {
            final String respString = MD5Util.compareMd5ResultString (changedGroups);
            // Disable cache.
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-cache,no-store");
            response.setStatus(HttpServletResponse.SC_OK);
            //response.getWriter().println(respString);
            response.getWriter().println(respString);
            response.setHeader("tyh", respString);
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
        }
    }

 class StatTask implements Runnable{

     @Override
     public void run() {
         log.info("[long-pulling] client count " + allSubs.size());
     }
 }
 //数据变更任务
 class DataChangeTask implements Runnable{
     final long changeTime = System.currentTimeMillis();
     String groupKey;
     Map<String,String> configData;
     public DataChangeTask(String groupKey,Map<String,String> configData){
            this.groupKey =groupKey;
            this.configData = configData;
      }
     @Override
     public void run() {
         //数据变更，遍历取出轮询得列
         allSubs.forEach (
                 clientLongPolling -> {
                     try {
                         allSubs.remove ();
                         //更新缓存配置
                         HttpServletRequest request = (HttpServletRequest)clientLongPolling.asyncContext.getRequest ();
                         String appName = request.getHeader (LongPollingService.CLIENT_APPNAME_HEADER);
                         List<String> changedGroups = MD5Util.compareUptoConfigList (configData, clientLongPolling.clientConfigMap);
                         clientLongPolling.sendResponse (changedGroups);
                         log.info ("{}|{}|{}|{}",System.currentTimeMillis ()-changeTime,groupKey,
                                 ((HttpServletRequest)clientLongPolling.asyncContext.getRequest ())
                                         .getHeader (LongPollingService.CLIENT_APPNAME_HEADER)
                                 ,"polling");
                     }catch (Throwable e){
                     }
                 }
         );
     }
 }
 //长轮询任务
 class ClientLongPolling implements Runnable{
      AsyncContext asyncContext;
      long timeout;
      String ip;
      Map<String, String> clientConfigMap;
      Future<?> asyncTimeoutFuture;
      public ClientLongPolling(AsyncContext asyncContext, long timeout,String ip,Map<String, String> clientConfigMap) {
          this.timeout=timeout;
          this.asyncContext=asyncContext;
          this.ip = ip;
          this.clientConfigMap=clientConfigMap;
      }

      @Override
      public void run() {
          asyncTimeoutFuture = service.schedule (new Runnable () {
              @Override
              public void run() {
                  //在队列中取出当前轮询任务，进行执行
                  allSubs.remove(ClientLongPolling.this);
                  HttpServletRequest request = (HttpServletRequest)asyncContext.getRequest ();
                  String appName = (String) request.getHeader (LongPollingService.CLIENT_APPNAME_HEADER);
                  List<String> changedGroups = MD5Util.compareUptoConfigList (ConfigCache.getConfigCasheMapByAppName (appName),clientConfigMap);
                  //是否有属性修改
                  if(changedGroups.size ()>0){
                      sendResponse(changedGroups);
                  }else {
                      sendResponse(null);
                  }
              }
          },timeout,TimeUnit.MILLISECONDS);
          //将当前轮询任务加入队列
          allSubs.add (this);
      }
     void sendResponse(List<String> changedGroups) {

         // Cancel time out task.-关闭当前线程
         if (null != asyncTimeoutFuture) {
             asyncTimeoutFuture.cancel(false);
         }
         generateResponse(changedGroups);
     }
     void generateResponse(List<String> changedGroups) {
         if (null == changedGroups) {

             // Tell web container to send http response.
             asyncContext.complete();
             return;
         }

         HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();

         try {
             final String respString = MD5Util.compareMd5ResultString(changedGroups);
             // Disable cache.
             response.setHeader("Pragma", "no-cache");
             response.setDateHeader("Expires", 0);
             response.setHeader("Cache-Control", "no-cache,no-store");
             response.setStatus(HttpServletResponse.SC_OK);
            // response.getWriter().println("respString-tyh-test-seccouss");
             response.setHeader("tyh", "respString-tyh-test-seccouss");
             asyncContext.complete();
         } catch (Exception ex) {
             System.out.println (ex.toString());
             asyncContext.complete();
         }
     }
  }
}
