package com.mydemo.toolslist.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER,
         ElementType.LOCAL_VARIABLE, ElementType.CONSTRUCTOR})
public @interface Teachers {
    String subject() default "name";
    int grade() default 3;
    public static final int Man = 1;
    public static final int Woman = 2;
}
