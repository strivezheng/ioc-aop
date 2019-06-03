package com.mySpring.client;

import com.mySpring.aop.SurroundAdvice;

/**
 * Created by 10033 on 2017/5/12.
 */
public class SurroundTest implements SurroundAdvice {
    @Override
    public void before() {
        System.out.println("surround  Before~~~~");
    }

    @Override
    public void after() {
        System.out.println("surround  After~~~~");
    }
}
