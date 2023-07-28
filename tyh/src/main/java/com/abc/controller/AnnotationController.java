package com.abc.controller;

import com.abc.annotation.Description;
import com.abc.annotation.ColorAnnotation;
import com.abc.annotation.MyAnnotation;
import com.abc.bean.Box;
import com.abc.bean.Product;
import com.abc.service.ShopService;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: AnnotationController
 * @Author: 青衣醉
 * @Date: 2022/7/20 2:07 下午
 */
@RestController
@RequestMapping
public class AnnotationController {
    @Autowired
    ShopService shopService;
    @Test
    public  void testAnnotation() throws NoSuchMethodException {
        Class<AnnotationController> annotationControllerClass = AnnotationController.class;
        Method[] methods = annotationControllerClass.getMethods ();
        Annotation[] annotations = annotationControllerClass.getAnnotations ();
        Annotation[] declaredAnnotation = annotationControllerClass.getDeclaredAnnotations ();
        Annotation annotation = annotationControllerClass.getAnnotation (RestController.class);
        Method test = annotationControllerClass.getMethod ("test");

        List<Product> products = shopService.listAllProducts ();
        MyAnnotation annotationMethod = test.getAnnotation (MyAnnotation.class);
        Annotation[] annotationMethods = test.getAnnotations ();

        System.out.println ("注解："+annotationMethod.value());
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
