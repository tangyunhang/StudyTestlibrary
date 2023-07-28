package com.abc.client.controller;

import com.abc.annotation.Description;
import com.abc.annotation.ColorAnnotation;
import com.abc.annotation.MyAnnotation;
import com.abc.annotation.aop.MyLogAnnotation;
import com.abc.aop.proxy.CglibAopProxyBeanFactory;
import com.abc.bean.Box;
import com.abc.bean.Product;
import com.abc.bean.Trade;
import com.abc.service.ShopService;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: AnnotationController
 * @Author: 青衣醉
 * @Date: 2022/7/20 2:07 下午
 */
@RestController
@RequestMapping("/tyh/annot")
public class AnnotationController {
    @Autowired
    ShopService shopService;

    @Autowired
    ApplicationContext context;
    @MyLogAnnotation(value = "测试aop")
    @GetMapping("/get/{name}")
    public  void testAnnotation(@PathVariable("name") String name){
        System.out.println (name);
    }

    //@MyLogAnnotation(value = "测试aop")
    // @PostMapping("/save")
    // public  void saveAnnotation( @RequestBody Trade params){
    //     System.out.println (1);
    // }
    // @RequestMapping("/save")
    // @MyLogAnnotation(value = "测试aop")
    // public boolean saveProduct(@RequestParam Map<String,Object> product){
    //     return true;//shopService.saveProduct (product);
    // }
    @RequestMapping("/save")
    @MyLogAnnotation(value = "测试aop")
    public boolean saveProduct(@RequestBody Product product){
        return true;//shopService.saveProduct (product);
    }
    // @MyLogAnnotation(value = "测试aop")
    @GetMapping("/testAop/{name}")
    // @MyLogAnnotation
    @SneakyThrows
    public List<Product> testCstomAop(@PathVariable("name") String name){
        // ShopService proxyCgBean = (ShopService)context.getBean ("shopServiceImpl");
        // Method listAllProducts = proxyCgBean.getClass ().getMethod ("listAllProducts");
        // listAllProducts.getAnnotations ();
       //  JDKAopProxyBeanFactory jdkAopProxyBeanFactory = new JDKAopProxyBeanFactory ();
       // ShopService proxyBean = (ShopService)jdkAopProxyBeanFactory.getProxyBean (shopService);
        CglibAopProxyBeanFactory cjlibAopProxyBeanFactory = new CglibAopProxyBeanFactory ();
        ShopService proxyCgBean = (ShopService)cjlibAopProxyBeanFactory.getProxyBean (shopService);
         return proxyCgBean.listAllProducts ();
        //return Arrays.asList (proxyBean.getProduct (name));
    }

    @SneakyThrows
    @Test
    @MyAnnotation("我的盒子是一个")
    public void test(){
        Box box = new Box ("苹果",10,20,null);
        Class<Box> boxClass = (Class<Box>) box.getClass ();
        MyAnnotation test = this.getClass ().getMethod ("test").getAnnotation (MyAnnotation.class);
        ColorAnnotation colorField = boxClass.getDeclaredField ("name").getAnnotation (ColorAnnotation.class);
        Map<String, Description> fieldMap = this.getFieldMapAnnotatedWith (boxClass, Description.class);
        StringBuilder sp = new StringBuilder (test.value ());
        String color = colorField.value ();
        String high = fieldMap.get ("high").value ();
        String width = fieldMap.get ("width").value ();
        String desc = fieldMap.get ("desc").value ();
        String s = sp.append (high)
                .append (box.getHigh ())
                .append (",")
                .append (width)
                .append (",")
                .append (box.getWidth ())
                .append (",")
                .append (color)
                .append (box.getName ())
                .append (desc).toString ();
        System.out.println (s);
    }
    private <T extends Annotation >Map<String,T> getFieldMapAnnotatedWith(Class<? extends Object> object, Class<T> annotation) {
        if (Object.class.equals (object)) {
            return Collections.emptyMap ();
        }
        Field[] declaredFields = object.getDeclaredFields ();
        return Arrays.stream (declaredFields)
                .filter (field -> field.getAnnotation (annotation) != null)
                .collect (Collectors.toMap (field -> field.getName (), field -> field.getAnnotation (annotation)));
    }
    private <T extends Annotation >Set<Field> getFieldAnnotatedWith(Class<? extends Object> object, Class<T> annotation) {
        if (Object.class.equals (object)) {
            return Collections.emptySet ();
        }
        Field[] declaredFields = object.getDeclaredFields ();
        return Arrays.stream (declaredFields)
                .filter (field -> field.getAnnotation (annotation) != null)
                .collect (Collectors.toSet ());
    }
}
