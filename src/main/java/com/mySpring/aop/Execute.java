package com.mySpring.aop;

import com.mySpring.aop.advice.AfterAdvice;
import com.mySpring.aop.advice.BeforeAdvice;
import com.mySpring.aop.advice.SurroundAdvice;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Created by seven on 2018/5/12.
 * 执行通知
 */
public class Execute {

    /**
     * 执行after
     *
     * @param o
     * @param objects
     * @param methodProxy
     * @param advice
     * @return
     * @throws Throwable
     */
    public static Object executeAfter(Object o, Object[] objects, MethodProxy methodProxy, AfterAdvice advice) throws Throwable {
        Object object = methodProxy.invokeSuper(o, objects);
        advice.after();
        return object;
    }

    /**
     * 执行before
     *
     * @param o
     * @param objects
     * @param methodProxy
     * @param advice
     * @return
     * @throws Throwable
     */
    public static Object executeBefore(Object o, Object[] objects, MethodProxy methodProxy, BeforeAdvice advice) throws Throwable {
        advice.before();
        Object object = methodProxy.invokeSuper(o, objects);

        return object;
    }

    /**
     * 执行 surround
     *
     * @param o
     * @param objects
     * @param methodProxy
     * @param advice
     * @return
     * @throws Throwable
     */
    public static Object executeSurround(Object o, Object[] objects, MethodProxy methodProxy, SurroundAdvice advice) throws Throwable {
        advice.before();
        Object object = methodProxy.invokeSuper(o, objects);
        advice.after();
        return object;
    }
}
