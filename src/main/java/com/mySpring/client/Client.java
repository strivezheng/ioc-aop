package com.mySpring.client;


import com.mySpring.autowired.BeanFactory;
import net.sf.cglib.core.DebuggingClassWriter;

/**
 * Created by 10033 on 2017/5/9.
 */
public class Client {
    public static void main(String[] args) throws ClassNotFoundException {

        //设置cglib生成代理类class文件的位置
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\classes");

        Class.forName("com.mySpring.autowired.BeanFactory");

        Classes classes= (Classes) BeanFactory.getBean("classes");
        Teacher teacher= (Teacher) BeanFactory.getBean("teacher");
        Student student= (Student) BeanFactory.getBean("student");

        classes.showClasses();
//        student.showName();
//        classes.test();
//        student.show();


        /*Field[] fields=classes.getClass().getDeclaredFields();
        for(Field field:fields) {
            System.out.println(field.getName());
        }*/
    }
}
