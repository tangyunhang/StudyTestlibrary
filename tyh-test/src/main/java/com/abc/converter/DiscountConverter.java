package com.abc.converter;

import com.abc.discount.Discount;
import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @Description: DiscountConverter
 * @Author: 青衣醉
 * @Date: 2022/7/18 3:01 下午
 */
@Converter
public class DiscountConverter implements AttributeConverter<Discount,String> {
    @Override
    public String convertToDatabaseColumn(Discount discount) {
        return JSON.toJSONString (discount);
    }

    @Override
    public Discount convertToEntityAttribute(String s) {
        return JSON.parseObject (s,Discount.class);
    }
}
