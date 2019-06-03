package com.mySpring.autowired;

import java.lang.annotation.*;

@Target({ElementType.FIELD,  ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited  
@Documented 
public @interface MyAutowired {
}
