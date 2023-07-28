package com.abc.bean;

import lombok.Data;
import org.springframework.stereotype.Component;
/**
 * @Description: Usermanager
 * @Author: 青衣醉
 * @Date: 2022/6/10 5:12 下午
 */
@Component
public class Usermanager {

    private String username;
    /*
     * @description: 
     * @author: tangyunhang 
     * @date: 2022/6/10 5:10 下午
     * @param: []
     * @return:
     **/
    public Usermanager(){
        System.out.println("调用无参构造 this="+this);
    }
    public Usermanager(String username){
        System.out.println("调用参构造 this="+this + ",name=" + username);
    }
}