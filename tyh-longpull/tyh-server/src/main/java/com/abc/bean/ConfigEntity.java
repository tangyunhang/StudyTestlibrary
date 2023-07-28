package com.abc.bean;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: ConfigInfo
 * @Author: 青衣醉
 * @Date: 2022/9/5 5:28 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "config_Info")
public class ConfigEntity implements Serializable {
    @Id//声明当前属性为主键id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//配置主键生成策略：使用数据库主键自增策略
    private Integer id;
    private String dataId;

    private String groupkey;

    private String configInfo;

    private boolean isActive;

    private String vartionDate;

    @Column(name = "created_time", nullable = false)
    private Date createdTime;

}
