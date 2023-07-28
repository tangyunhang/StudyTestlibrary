package com.abc.bean;

import com.abc.discount.Discount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: OrderInfo
 * @Author: 青衣醉
 * @Date: 2022/7/18 1:41 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInfo {
    String orderId;
    //已选商品
    List<Product> products;
    //已选优惠
    String code;

    Long cost;

    List<Discount> discount;


}
