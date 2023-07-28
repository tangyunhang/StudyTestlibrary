package com.abc.calculation;

import com.abc.bean.OrderInfo;
import com.abc.calculation.impl.DiscountTemplate;
import com.abc.calculation.impl.EmptyTemplate;
import com.abc.calculation.impl.MoneyOffTemplate;
import com.abc.calculation.impl.RandomDiscountTemplate;
import com.abc.enmus.DiscountType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * @Description: DisCountFactory
 * @Author: 青衣醉
 * @Date: 2022/7/18 2:08 下午
 */
@Component
public  class DisCountTemplateFactory {
    @Autowired
    EmptyTemplate emptyTemplate;
    @Autowired
    DiscountTemplate discountTemplate;
    @Autowired
    MoneyOffTemplate moneyOffTemplate;
    @Autowired
    RandomDiscountTemplate randomDiscountTemplate;

    public RuleTemplate getTemplate(OrderInfo orderInfo){
        if (StringUtils.isEmpty (orderInfo.getCode ())){
            return emptyTemplate;
        }

        DiscountType convert = DiscountType.convert (orderInfo.getCode ());
        switch (convert) {
            case UNKNOWN:
                return emptyTemplate;
            case MONEY_OFF:
                return moneyOffTemplate;
            case DISCOUNT:
                return discountTemplate;
            case RANDOM_DISCOUNT:
                return randomDiscountTemplate;
            default:
                return emptyTemplate;
        }
    }
}
