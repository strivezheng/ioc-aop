package com.mySpring.aop;

import net.sf.cglib.proxy.MethodProxy;

/**
 * Created by 10033 on 2017/5/12.
 * 执行通知
 */
public class Execute {
    public static Object executeAfter
            (Object o, Object[] objects, MethodProxy methodProxy, AfterAdvice advice) throws Throwable {
        Object object=methodProxy.invokeSuper(o,objects);
        advice.after();
        return object;
    }
    public static Object executeBefore
            (Object o, Object[] objects, MethodProxy methodProxy, BeforeAdvice advice) throws Throwable {
        advice.before();
        Object object=methodProxy.invokeSuper(o,objects);

        return object;
    }
    public static Object executeSurround
            (Object o, Object[] objects, MethodProxy methodProxy, SurroundAdvice advice) throws Throwable {
        advice.before();
        Object object=methodProxy.invokeSuper(o,objects);
        advice.after();
        return object;
    }
}
