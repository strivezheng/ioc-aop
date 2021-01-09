package com.mySpring.autowired;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by seven on 2018/5/12.
 * 自动注入类
 */
public class AutomaticInjection {


    /**
     * 将bean实例化，并且填充到map中
     * @param key
     * @param mmp
     */
    public static void automaticInjection(String key, Map mmp) {
        try {
            List<Class> list = GetClass.getClassList(key);

            for (Class classes : list) {
                //用于判断是否有循环依赖
                Map<String, Object> judgeMap = new HashMap();
                injection(mmp, classes, judgeMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注入并判断是否循环依赖
     * @param mmp       存放实例化后的bean
     * @param classes   需要实例化的类的class
     * @param judgeMap  一个flag，因为是递归调用此方法的，判断是否有循环依赖，在外层for循环初始化为空
     * @throws Exception
     */
    private static void injection(Map mmp, Class classes, Map judgeMap)
            throws Exception {
        boolean isExist = classes.isAnnotationPresent(MyBean.class);
        //如果该注解存在
        if (isExist) {
            MyBean myBean = (MyBean) classes.getAnnotation(MyBean.class);
            String beanName = myBean.value(); //获得bean名称
            if (null == judgeMap.get(beanName))
                judgeMap.put(beanName, true);
            else { //又返回依赖他
                throw new Exception("循环依赖");
            }

            if (null == mmp.get(beanName)) { //还没有被注入
                Object beanObj = classes.newInstance(); //获得bean实例

                Field[] fields = classes.getDeclaredFields();
                boolean fieldExist;
                for (Field field : fields) {
                    fieldExist = field.isAnnotationPresent(MyAutowired.class);

                    if (fieldExist) {//存在需要注入的属性
                        String classtype = field.getGenericType().toString();
                        String filedClassName = classtype.substring(6);
                        Class fieldClass = Class.forName(filedClassName);

                        //强制设置值 破坏了封装性
                        field.setAccessible(true);

                        if (fieldClass.isAnnotationPresent(MyBean.class)) {
                            //该属性依赖其它Bean，递归实例化被依赖的bean
                            MyBean tbean = (MyBean) fieldClass.getAnnotation(MyBean.class);
                            injection(mmp, fieldClass, judgeMap);
                            field.set(beanObj, mmp.get(tbean.value()));

                        } else {
                            //该属性不依赖其它Bean，直接实例化自己
                            Object object = fieldClass.newInstance();
                            field.set(beanObj, object);
                        }
                    }
                }
                mmp.put(beanName, beanObj);
            }

        }
    }

    /**
     * 重新填充被cglib代理生成属性
     * @param mmp
     * @param classes
     * @param obj
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void reinjection(Map mmp, Class classes, Object obj) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException {
        Field[] fields = classes.getDeclaredFields();
        boolean fieldExist;
        for (Field field : fields) {
            fieldExist = field.isAnnotationPresent(MyAutowired.class);

            if (fieldExist) {
                String classtype = field.getGenericType().toString();
                String filedClassName = classtype.substring(6);
                Class fieldClass = Class.forName(filedClassName);
                //强制设置值 破坏了封装性
                field.setAccessible(true);

                if (fieldClass.isAnnotationPresent(MyBean.class)) {//该属性依赖其它Bean
                    MyBean tbean = (MyBean) fieldClass.getAnnotation(MyBean.class);
                    field.set(obj, mmp.get(tbean.value()));

                } else { //该属性不依赖其它Bean
                    Object object = fieldClass.newInstance();
                    field.set(obj, object);
                }
            }
        }
    }

}
