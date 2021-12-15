package com.mydemo.toolslist.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IntDef;

import static com.mydemo.toolslist.annotation.WeekDay.TUESDAY;
import static com.mydemo.toolslist.annotation.WeekDay.MONDAY;

@IntDef({MONDAY, TUESDAY})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.SOURCE)
public @interface WeekDay {
    static final int MONDAY = 1;
    static final int TUESDAY = 2;
}
