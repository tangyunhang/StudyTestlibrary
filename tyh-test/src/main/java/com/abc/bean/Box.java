package com.abc.bean;

import com.abc.annotation.ColorAnnotation;
import com.abc.annotation.Description;
import com.abc.annotation.MyAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description: Box
 * @Author: 青衣醉
 * @Date: 2022/7/20 4:48 下午
 */
@Data
@AllArgsConstructor
public class Box {
    @ColorAnnotation(value = "绿色",color = ColorAnnotation.COLOR.GREEN)
    String name;
    @Description("高")
    Integer high;
    @Description("宽")
    Integer width;
    @Description("礼盒")
    String desc;
}
