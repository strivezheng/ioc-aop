package com.mySpring.aop.annotation;

import java.lang.annotation.*;

/**
 * Created by seven on 2018/5/12.
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Ignore {
}
