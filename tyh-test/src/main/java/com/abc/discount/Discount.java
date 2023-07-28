package com.abc.discount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: discount
 * @Author: 青衣醉
 * @Date: 2022/7/15 4:54 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
    // 满减 - 减掉的钱数
    // 折扣 - 90 = 9折,  95=95折
    private Long quota;

    // 最低达到多少消费才能用
    private Long threshold;
}
