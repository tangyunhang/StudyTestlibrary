package com.abc.event;

import com.abc.bean.OrderInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @Description: EmailApplicationEvent
 * @Author: 青衣醉
 * @Date: 2022/7/19 4:36 下午
 */
@Getter
@Setter
public class EmailApplicationEvent extends ApplicationEvent {
    //订单信息
    OrderInfo orderInfo;
    public EmailApplicationEvent(Object source) {
        super (source);
    }
    public EmailApplicationEvent(Object source,OrderInfo orderInfo){
        super(source);
        this.orderInfo = orderInfo;
    }
}
