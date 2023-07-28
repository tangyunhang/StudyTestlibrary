package com.test.nn;

import lombok.Data;
import org.springframework.util.Assert;

/**
 * @Description: TRDT
 * @Author: 青衣醉
 * @Date: 2023/4/17 2:46 下午
 */
public class TRDT {
    public static void main(String[] args) {
        bb bb = new bb ();
        Assert.notNull(bb.getCc (), "Source must not be null");
    }

    @Data
    static
    class bb {
        String cc;
    }
}
