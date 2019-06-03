package com.mySpring.client;

import com.mySpring.aop.AfterAdvice;

/**
 * Created by 10033 on 2017/5/12.
 */
public class AfterTest implements AfterAdvice {
    @Override
    public void after() {
        System.out.println("after~~~~~~");
    }
}
