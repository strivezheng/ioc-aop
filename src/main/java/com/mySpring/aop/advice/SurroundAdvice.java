package com.mySpring.aop.advice;

/**
 * Created by seven on 2018/5/12.
 *
 */
public interface SurroundAdvice extends Advice {
    void before();
    void after();
}
