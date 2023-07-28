package com.abc.event;

import com.abc.bean.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description: MessageEvent
 * @Author: 青衣醉
 * @Date: 2022/7/19 4:41 下午
 */
@Data
@AllArgsConstructor
public class MessageEvent {
    OrderInfo orderInfo;
}
