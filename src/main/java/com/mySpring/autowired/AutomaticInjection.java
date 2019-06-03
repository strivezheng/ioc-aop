package com.mySpring.autowired;

/**
 * Created by 10033 on 2017/5/9.
 */

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动注入类
 */
public class AutomaticInjection {

    public static void automaticInjection(String key, Map mmp) {
        try {
            List<Class> list = GetClass.getClassList(key);

            for(Class classes:list) {
                Map<String, Object> judgeMap = new HashMap();
                injection(mmp,classes,judgeMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //注入并判断是否循环依赖
    private static void injection(Map mmp, Class classes, Map judgeMap)
            throws Exception {
        boolean isExist = classes.isAnnotationPresent(MyBean.class);
        //如果该注解存在
        if(isExist) {
            MyBean myBean = (MyBean) classes.getAnnotation(MyBean.class);
            String beanName= myBean.value(); //获得bean名称
            if(null==judgeMap.get(beanName))
                judgeMap.put(beanName,true);
            else { //又返回依赖他
                throw new Exception("循环依赖");
            }

            if(null==mmp.get(beanName)) { //还没有被注入
                Object beanObj=classes.newInstance(); //获得bean实例

                Field[] fields=classes.getDeclaredFields();
                boolean fieldExist;
                for(Field field:fields) {
                    fieldExist=field.isAnnotationPresent(MyAutowired.class);

                    if(fieldExist) {
                        String classtype=field.getGenericType().toString();
                        Class fieldClass=Class.forName(classtype.substring(6));

                        //强制设置值 破坏了封装性
                        field.setAccessible(true);

                        if(fieldClass.isAnnotationPresent(MyBean.class)) {//该属性依赖其它Bean
                            MyBean tbean = (MyBean) fieldClass.getAnnotation(MyBean.class);
                            injection(mmp,fieldClass,judgeMap);
                            field.set(beanObj, mmp.get(tbean.value()));

                        }

                        else { //该属性不依赖其它Bean
                            Object object=fieldClass.newInstance();
                            field.set(beanObj, object);
                        }
                    }
                }
                mmp.put(beanName, beanObj);
            }

        }
    }

    public static void reinjection(Map mmp, Class classes, Object obj) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Field[] fields=classes.getDeclaredFields();
        boolean fieldExist;
        for(Field field:fields) {
            fieldExist=field.isAnnotationPresent(MyAutowired.class);

            if(fieldExist) {
                String classtype=field.getGenericType().toString();
                Class fieldClass=Class.forName(classtype.substring(6));
                field.setAccessible(true);
                //强制设置值 破坏了封装性
                field.setAccessible(true);

                if(fieldClass.isAnnotationPresent(MyBean.class)) {//该属性依赖其它Bean
                    MyBean tbean = (MyBean) fieldClass.getAnnotation(MyBean.class);
                    field.set(obj, mmp.get(tbean.value()));

                }else { //该属性不依赖其它Bean
                    Object object=fieldClass.newInstance();
                    field.set(obj, object);
                }
            }
        }
    }

}
