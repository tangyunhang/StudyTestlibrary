package com.abc.event;

import com.abc.bean.Product;
import com.abc.discount.Discount;
import com.abc.enmus.DiscountType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 购物事件
 * @Author: 青衣醉
 * @Date: 2022/6/15 2:52 下午
 */
@Getter
@Setter
public class CalculationApplicationEvent extends ApplicationEvent {
    //商品
    private List<Product> products;
    //优惠编号
    private String code;

    public CalculationApplicationEvent(Object source) {
        super (source);
    }

    public CalculationApplicationEvent(Object source,List<Product> products,String code){
        super (source);
        this.products = products;
        this.code = code;
    }
}
