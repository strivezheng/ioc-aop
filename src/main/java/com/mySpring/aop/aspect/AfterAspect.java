package com.mySpring.aop.aspect;

import com.mySpring.aop.advice.AfterAdvice;

/**
 * Created by seven on 2018/5/12.
 */
public class AfterAspect implements AfterAdvice {
    @Override
    public void after() {
        System.out.println("after~~~~~~");
    }
}
