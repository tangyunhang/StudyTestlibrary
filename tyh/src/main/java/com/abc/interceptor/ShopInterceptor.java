package com.abc.interceptor;


import com.abc.annotation.IgnoreToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Description: ShopInterceptor
 * @Author: 青衣醉
 * @Date: 2022/8/4 4:06 下午
 */
@Component
@Slf4j

public class ShopInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("进入拦截器,URL:{}", request.getServletPath());
        Method method = ((HandlerMethod) handler).getMethod ();
        if(method.isAnnotationPresent (IgnoreToken.class)){
            System.out.println (method.getName ()+"方法使用IgnoreToken注解被拦截");
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
