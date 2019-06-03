package com.mySpring.client;


import com.mySpring.aop.PointCut;
import com.mySpring.autowired.MyAutowired;
import com.mySpring.autowired.MyBean;

/**
 * Created by 10033 on 2017/5/10.
 */
@MyBean("student")
@PointCut("com.mySpring.client.BeforeTest")
public class Student {
    @MyAutowired
    private Teacher teacher;
    @MyAutowired
    private String name;

    public void showName() {
        name="hehe";
        System.out.println(name);
    }

//    @PointCut("com.mySpring.client.SurroundTest")
    public void show() {
        name="嘻嘻";
        System.out.println(name);
        System.out.println(teacher);
    }
}
