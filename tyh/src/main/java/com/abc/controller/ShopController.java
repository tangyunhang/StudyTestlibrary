package com.abc.controller;

import com.abc.annotation.IgnoreToken;
import com.abc.bean.OrderInfo;
import com.abc.bean.Product;
import com.abc.service.OrderService;
import com.abc.service.ShopService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description: ShopController购物
 * @Author: 青衣醉
 * @Date: 2022/7/15 2:42 下午
 */
@RestController
@RequestMapping("/tyh/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    @Autowired
    OrderService orderService;

    @PostMapping("/save")
    public boolean saveProduct(@RequestBody Product product){
        return shopService.saveProduct (product);
    }

    @GetMapping("/order/{code}")
    public void order(@PathVariable String code){
        OrderInfo orderInfo = Optional.ofNullable (shopService.listAllProducts ())
                .map (proudcts -> {
                    return OrderInfo.builder ()
                            .orderId ("DD8721231303")
                            .products (proudcts)
                            .code (code)
                            .build ();})
                .get ();
        orderService.order(orderInfo);
    }
    @IgnoreToken
    @GetMapping("/get")
    public void getShop(){

        List<Product> products = shopService.listAllProducts();
    }

    //查询列表
    @DeleteMapping("/delete/{id}")
    public void deleteProductByName(@PathVariable("id") Integer id){
        shopService.deleteProductById(id);
    }

    //查询列表
    @GetMapping("/list")
    public List<Product> listHandle(){

        ArrayList<Product> products = Lists.newArrayList ();
        //applicationContext.publishEvent (new ShopApplicationEvent ());
        return shopService.listAllProducts();
    }
}
