package com.mySpring.aop;

/**
 * Created by 10033 on 2017/5/12.
 */

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 通过注解判断执行哪个通知
 */
public class ProxyController {

    //没有类注解
    public static Object doController
            (Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //有忽视注解
        if(method.isAnnotationPresent(Ignore.class))
            return methodProxy.invokeSuper(o, objects);
        //没有切入点
        if(!method.isAnnotationPresent(PointCut.class)) {
            return methodProxy.invokeSuper(o, objects);
        }else { //有切入点
            Advice advice=getAdvice(method);
            return doAdvice(o,objects,methodProxy,advice);
        }

    }

    //有类注解
    public static Object doController
        (Object o, Method method, Object[] objects, MethodProxy methodProxy, Advice advice) throws Throwable {
        //有忽视注解
        if(method.isAnnotationPresent(Ignore.class))
            return methodProxy.invokeSuper(o, objects);
        //有切入点
        if(method.isAnnotationPresent(PointCut.class)) {
            Advice advice2=getAdvice(method);
            return doAdvice(o,objects,methodProxy,advice2);
        } else { //没有切入点
            return doAdvice(o,objects,methodProxy,advice);
        }

    }

    private static Object doAdvice(Object o, Object[] objects, MethodProxy methodProxy, Advice advice) throws Throwable {
        if(advice instanceof AfterAdvice) {
            return Execute.executeAfter(o,objects,methodProxy, (AfterAdvice) advice);
        }else if(advice instanceof BeforeAdvice) {
            return Execute.executeBefore(o,objects,methodProxy, (BeforeAdvice) advice);
        }else if(advice instanceof SurroundAdvice) {
            return Execute.executeSurround(o,objects,methodProxy, (SurroundAdvice) advice);
        }
        return null;
    }

    private static Advice getAdvice(Method method) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String classPath=method.getAnnotation(PointCut.class).value();
        Advice advice= (Advice) Class.forName(classPath).newInstance();
        return advice;
    }
}
