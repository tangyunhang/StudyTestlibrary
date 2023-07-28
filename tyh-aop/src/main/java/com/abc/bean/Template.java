package com.abc.bean;

import com.abc.converter.DiscountConverter;
import com.abc.discount.Discount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * @Description: Template
 * @Author: 青衣醉
 * @Date: 2022/7/18 2:55 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Slf4j
public class Template {
    @Id//声明当前属性为主键id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//配置主键生成策略：使用数据库主键自增策略
    private Long id;
    private String name;
    private String code;
    private String description;
    // 优惠券核算规则，平铺成JSON字段
    @Convert(converter = DiscountConverter.class)
    private Discount rule;
}
