package com.mySpring.autowired;


import com.mySpring.aop.ProxyFactory;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seven on 2018/5/12.
 * <p>
 * 实例化Bean的Factory
 */
public class BeanFactory {

    /**
     * 最终存放bean实例的map
     * key：bean的name
     * value：bean的实例
     */
    public static Map<String, Object> beanMap = new HashMap();

    /**
     * 配置的需要扫描的包路径
     */
    private static final String KEY = "scan.package";

    /**
     * 初始化IoC容器
     */
    static {
        //实例化对象bean，填充到map中存储
        AutomaticInjection.automaticInjection(KEY, beanMap);

        //将实际的bean设置成cglib代理的bean
        ProxyFactory.makeProxyBean(beanMap);

        //生成代理后重新注入,eg：classesService里面的成员变量teacherService此时是null、需要重新赋值
        for (String key : beanMap.keySet()) {
            Class c = beanMap.get(key).getClass().getSuperclass();
            try {
                AutomaticInjection.reinjection(beanMap, c, beanMap.get(key));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据beanName 返回bean
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return beanMap.get(name);
    }

}
