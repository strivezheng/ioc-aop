package com.mySpring.service;


import com.mySpring.aop.annotation.PointCut;
import com.mySpring.autowired.MyAutowired;
import com.mySpring.autowired.MyBean;

/**
 * Created by seven on 2018/5/12.
 */
@MyBean("studentService")
@PointCut("com.mySpring.aspect.BeforeAspect")
public class StudentService {

    // TODO: 2021/1/8  循环依赖
    /*
      @MyAutowired
      private Teacher teacher;
     */
    @MyAutowired
    private String name;

    public void showName() {
        name = "hehe";
        System.out.println(name);
    }

    //    @PointCut("com.mySpring.client.SurroundAspect")
    public void show() {
        name = "嘻嘻";
        System.out.println(name);
    }
}
