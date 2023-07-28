package com.abc.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * @Description: SysLogEntity
 * @Author: 青衣醉
 * @Date: 2022/8/9 1:49 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Slf4j
public class SysLogEntity {
    @Id//声明当前属性为主键id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//配置主键生成策略：使用数据库主键自增策略
    Long id;
    private String className;
    private String methodName;
    private String params;
    private Long exeuTime;
    private String remark;
    private String createDate;
}
