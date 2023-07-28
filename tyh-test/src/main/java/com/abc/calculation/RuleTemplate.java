package com.abc.calculation;

import com.abc.bean.OrderInfo;

public interface RuleTemplate {

    // 计算积分
    OrderInfo calculate(OrderInfo settlement);
}
