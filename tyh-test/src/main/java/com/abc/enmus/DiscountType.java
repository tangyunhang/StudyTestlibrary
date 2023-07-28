package com.abc.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @Description: 打折类型
 * @Author: 青衣醉
 * @Date: 2022/7/18 10:36 上午
 */
@Getter
@AllArgsConstructor
public enum DiscountType {
    UNKNOWN("unknown", "0"),
    MONEY_OFF("满减券", "1"),
    DISCOUNT("打折", "2"),
    RANDOM_DISCOUNT("随机减", "3");

    private String description;

    // 存在数据库里的最终code
    private String code;

    public static DiscountType convert(String code){
       return Stream.of (values ())
                .filter (discountType -> discountType.code.equals (code))
                .findFirst ()
                .orElse (UNKNOWN);
    }
}
