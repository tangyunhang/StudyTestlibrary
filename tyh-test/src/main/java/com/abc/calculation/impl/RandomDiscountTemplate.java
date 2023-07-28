package com.abc.calculation.impl;

import com.abc.calculation.AbstractRuleTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Description: RandomDiscountTemplate
 * @Author: 青衣醉
 * @Date: 2022/7/18 2:24 下午
 */
@Component
@Slf4j
public class RandomDiscountTemplate extends AbstractRuleTemplate {
    @Override
    protected Long calculateNewPrice(Long totalPrice, Long quota) {
        int reductionAmount = new Random ().nextInt(quota.intValue ());
        Long newCost = totalPrice - reductionAmount;

        log.debug("original price={}, new price={}", totalPrice, newCost );
        return newCost;
    }
}
