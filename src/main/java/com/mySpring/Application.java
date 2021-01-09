package com.mySpring;


import com.mySpring.autowired.BeanFactory;
import com.mySpring.service.ClassesService;
import com.mySpring.service.StudentService;
import com.mySpring.service.TeacherService;
import net.sf.cglib.core.DebuggingClassWriter;

import java.io.File;

/**
 * Created by seven on 2018/5/12.
 */
public class Application {
    public static void main(String[] args) throws ClassNotFoundException {
        //初始化环境
        init();

        //模拟spring启动
        Class.forName("com.mySpring.autowired.BeanFactory");

        ClassesService classesService = (ClassesService) BeanFactory.getBean("classesService");
        TeacherService teacherService = (TeacherService) BeanFactory.getBean("teacherService");
        StudentService studentService = (StudentService) BeanFactory.getBean("studentService");

        classesService.showClasses();
        System.out.println();

        teacherService.show();
        System.out.println();

        studentService.show();

    }

    private static void init() {
        String cglibClassPath = "D:\\classes";

        File file = new File(cglibClassPath);
        if (!file.exists()) {
            file.mkdir();
        }
        //设置cglib生成代理类class文件的位置
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, cglibClassPath);
    }
}
