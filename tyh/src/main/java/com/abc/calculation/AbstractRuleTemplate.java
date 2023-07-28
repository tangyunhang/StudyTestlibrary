package com.abc.calculation;

import com.abc.bean.OrderInfo;
import com.abc.bean.Product;
import com.abc.bean.Template;
import com.abc.discount.Discount;
import com.abc.repository.TemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: AbstractRuleTemplate
 * @Author: 青衣醉
 * @Date: 2022/7/18 2:18 下午
 */
@Slf4j
public abstract class AbstractRuleTemplate implements RuleTemplate{

    @Autowired
    TemplateRepository repository;
    // 计算积分
    public OrderInfo calculate(OrderInfo orderInfo){
        //总价
        Long totalPrice = getTotalPrice(orderInfo.getProducts ());
        Template template = repository.findByCode (orderInfo.getCode ());
        Discount rule = template.getRule ();
        //优惠规则
        Long quota = rule.getQuota ();
        //最低消费
        Long threshold = rule.getThreshold ();
        //不满足最低消费，按原价走
        if (threshold>totalPrice) {
            log.warn("Totals of amount not meet, ur coupons are not applicable to this order");
            orderInfo.setCost (totalPrice);
            orderInfo.setDiscount (Lists.newArrayList ());
            return orderInfo;
        }
        Long newTotalPrice = calculateNewPrice (totalPrice, quota);
        orderInfo.setCost (newTotalPrice);
        orderInfo.setDiscount (Arrays.asList (rule));
        return orderInfo;
    }

    protected Long getTotalPrice(List<Product> products){
        return products.stream ()
                .mapToLong (product->product.getCount ()*product.getPrice ())
                .sum ();
    }
    // 金额计算具体逻辑，延迟到子类实现
    abstract protected Long calculateNewPrice(Long totalPrice, Long quota);

    protected long convertToDecimal(Double value) {
        return new BigDecimal (value).setScale(0, RoundingMode.HALF_UP).longValue();
    }

}
