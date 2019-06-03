package com.mySpring.client;


import com.mySpring.aop.PointCut;
import com.mySpring.autowired.MyBean;

/**
 * Created by 10033 on 2017/5/9.
 */
@MyBean("teacher")
@PointCut("com.mySpring.client.SurroundTest")
public class Teacher {
    /*@MyAutowired
    private Student student;*/
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sevenWantToSay(){
        System.out.println("hello world");
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                '}';
    }
}
