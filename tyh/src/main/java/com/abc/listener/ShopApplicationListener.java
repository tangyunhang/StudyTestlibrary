package com.abc.listener;

import com.abc.event.ShopApplicationEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Description: 监听购物事件，计算购物积分
 * @Author: 青衣醉
 * @Date: 2022/6/15 2:51 下午
 */
@Component
public class ShopApplicationListener implements ApplicationListener<ShopApplicationEvent> {
    @Override
    public void onApplicationEvent(ShopApplicationEvent applicationEvent) {


    }
}
