package com.mySpring.service;


import com.mySpring.aop.annotation.PointCut;
import com.mySpring.autowired.MyAutowired;
import com.mySpring.autowired.MyBean;

/**
 * Created by seven on 2018/5/12.
 */
@MyBean("teacherService")
@PointCut("com.mySpring.aop.aspect.SurroundAspect")
public class TeacherService {
    @MyAutowired
    private StudentService studentService;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void show() {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("my name is" + String.valueOf(name));
    }


}
