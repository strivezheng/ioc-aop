package com.mySpring.aop;

import com.mySpring.aop.advice.Advice;
import com.mySpring.aop.annotation.PointCut;

import java.util.Map;

/**
 * Created by seven on 2018/5/12.
 * 对Bean进行AOP操作
 */
public class ProxyFactory {

    /**
     * 将bean生成代理
     * @param map
     */
    public static void makeProxyBean(Map<String, Object> map) {
        for (String key : map.keySet()) {

            AopProxy aopProxy = new AopProxy();

            Object o = map.get(key);
            Class classes = o.getClass();
            //判断是否有类注解
            if (classes.isAnnotationPresent(PointCut.class)) {

                PointCut pointCut = (PointCut) classes.getAnnotation(PointCut.class);
                String classPath = pointCut.value();
                try {
                    Advice advice = (Advice) Class.forName(classPath).newInstance();
                    aopProxy.setClassAdvice(advice);

                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
            map.put(key, aopProxy.getProxy(classes));
        }
    }
}
