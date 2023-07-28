package com.test;

import com.abc.aop.aspect.MyAspect;
import org.junit.Test;
import org.mockito.internal.configuration.ClassPathLoader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

/**
 * @Description: IocloadBeanTest
 * @Author: 青衣醉
 * @Date: 2022/8/30 3:47 下午
 */
public class IocloadBeanTest {

    @Test
    public void iocLoadBean(){
        //获取资源
        ClassPathResource resource =new ClassPathResource ("applicationaop.xml");
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        int i = reader.loadBeanDefinitions (resource);
        MyAspect myAspect = (MyAspect)factory.getBean ("myAspect");
        myAspect.before ();
       // Assert.isTrue (false);

    }
}
