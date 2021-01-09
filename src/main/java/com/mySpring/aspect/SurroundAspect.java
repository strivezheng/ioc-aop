package com.mySpring.aspect;

import com.mySpring.aop.advice.SurroundAdvice;

/**
 * Created by seven on 2018/5/12.
 */
public class SurroundAspect implements SurroundAdvice {

    ThreadLocal<Long> timeLocal = new ThreadLocal<>();

    @Override
    public void before() {
        timeLocal.set(System.currentTimeMillis());
        System.out.println("surround  Before~~~~");
    }

    @Override
    public void after() {
        long begin = timeLocal.get();
        long now = System.currentTimeMillis();
        System.out.println("切面计算执行耗时：" +( now - begin));
        System.out.println("surround  After~~~~");
    }
}
