package com.abc.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Description: Product
 * @Author: 青衣醉
 * @Date: 2022/7/15 2:38 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity//声明当前类为实体类
public class Product {
    @Id//声明当前属性为主键id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//配置主键生成策略：使用数据库主键自增策略
    private Integer id;
    private String name;
    private Long price;
    private Integer count;
}
