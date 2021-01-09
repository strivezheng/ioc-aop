package com.mySpring.aop.aspect;

import com.mySpring.aop.advice.BeforeAdvice;

/**
 * Created by seven on 2018/5/12.
 */
public class BeforeAspect implements BeforeAdvice {
    @Override
    public void before() {
        System.out.println("before~~~~~");
    }
}
