package com.mySpring.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by 10033 on 2017/5/12.
 * 获得代理类
 */
public class AopProxy implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();
    private Advice classAdvice=null;

    public void setClassAdvice(Advice classAdvice) {
        this.classAdvice = classAdvice;
    }

    public Object getProxy(Class clazz){
        //设置创建子类的类
        enhancer.setSuperclass(clazz);
        //设置回调
        enhancer.setCallback(this);

        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        Object object=null;
        //有类注解
        if(null!=classAdvice) {
            object=ProxyController.doController(o,method, objects,methodProxy,classAdvice);
        } else {
            object=ProxyController.doController(o,method,objects,methodProxy);
        }

        return object;
    }
}
