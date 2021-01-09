package com.mySpring.autowired;

import java.lang.annotation.*;


/**
 * Created by seven on 2018/5/12.
 *
 */
@Target({ElementType.FIELD,  ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited  
@Documented 
public @interface MyAutowired {
}
