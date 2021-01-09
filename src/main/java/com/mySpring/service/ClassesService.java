package com.mySpring.service;


import com.mySpring.aop.annotation.PointCut;
import com.mySpring.autowired.MyAutowired;
import com.mySpring.autowired.MyBean;


/**
 * Created by seven on 2018/5/12.
 * 班级类
 */
@MyBean("classesService")
@PointCut("com.mySpring.aspect.AfterAspect")
public class ClassesService {

    @MyAutowired
    private TeacherService teacherService;
    private String classname;

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public void showClasses() {
        teacherService.setName("苍老师");
        classname = "计算机班";
        System.out.println(classname);
    }

    @PointCut("com.mySpring.client.BeforeAspect")
    public void test() {
        System.out.println("做一下");
    }
}
