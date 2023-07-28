package com.abc.client.controller;

import com.abc.annotation.IgnoreToken;
import com.abc.aop.proxy.CglibAopProxyBeanFactory;
import com.abc.bean.Product;
import com.abc.service.ShopService;
import com.abc.threadPool.AsyncServiceImpl;
import com.abc.utils.SqlScriptExecHandler;
import com.alibaba.nacos.common.utils.ThreadUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    AsyncServiceImpl asyncServiceImpl;

    @Autowired
    SqlScriptExecHandler sqlScriptExecHandler;



    @PostMapping("/save")
    public boolean saveProduct(@RequestBody Product product){
        return shopService.saveProduct (product);
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
        CglibAopProxyBeanFactory cglibAopProxyBeanFactory = new CglibAopProxyBeanFactory ();
        ShopService proxyBean = (ShopService)cglibAopProxyBeanFactory.getProxyBean (shopService);
        return proxyBean.listAllProducts ();
        //return shopService.listAllProducts();
    }
    //查询列表
    @GetMapping("/test")
    public Object testHandle(){
        asyncServiceImpl.sendEmail ("we","ee","rr");
        asyncServiceImpl.sendSms ("11","22","33","44");
        sqlScriptExecHandler.executor.execute (()->{
            System.out.println (Thread.currentThread ().getName ()+1);
        });
        sqlScriptExecHandler.executor.execute (()->{
            System.out.println (Thread.currentThread ().getName ()+2);
        });
        return "ok";
    }

}
