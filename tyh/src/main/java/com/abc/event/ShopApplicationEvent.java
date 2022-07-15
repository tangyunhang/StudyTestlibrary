package com.abc.event;

import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @Description: 购物事件
 * @Author: 青衣醉
 * @Date: 2022/6/15 2:52 下午
 */
public class ShopApplicationEvent extends ApplicationEvent {
    //商品
    private List<String> products;
    //总价
    private double totalPrice;
    //折扣
    private double discount;
    public ShopApplicationEvent(Object source) {
        super (source);
    }

    public ShopApplicationEvent(Object source,List<String> products,double totalPrice,double discount){
        super(source);
        this.products = products;
        this.totalPrice = totalPrice;
        this.discount = discount;
    }
}
