package com.mySpring.aop;

/**
 * Created by 10033 on 2017/5/12.
 */
public interface SurroundAdvice extends Advice {
    void before();
    void after();
}
