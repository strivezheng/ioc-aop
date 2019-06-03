package com.mySpring.client;

/**
 * Created by 10033 on 2017/5/9.
 */


import com.mySpring.aop.PointCut;
import com.mySpring.autowired.MyAutowired;
import com.mySpring.autowired.MyBean;

/**
 * 班级类
 */
@MyBean("classes")
@PointCut("com.mySpring.client.AfterTest")
public class Classes {

    @MyAutowired
    private Teacher teacher;
    private String classname;

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public void showClasses() {
        teacher.setName("苍老师");
        classname="计算机班";
        System.out.println(classname);
        System.out.println(teacher);
    }

    @PointCut("com.mySpring.client.BeforeTest")
    public void test() {
        System.out.println("做一下");
    }
}
