package com.mySpring.autowired;

import java.lang.annotation.*;

/**
 * Created by 10033 on 2017/5/9.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MyBean {
    String value();
}
