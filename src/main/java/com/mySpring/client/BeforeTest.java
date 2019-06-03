package com.mySpring.client;

import com.mySpring.aop.BeforeAdvice;

/**
 * Created by 10033 on 2017/5/12.
 */
public class BeforeTest implements BeforeAdvice {
    @Override
    public void before() {
        System.out.println("before~~~~~");
    }
}
