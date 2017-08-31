package com.vicky.android.baselib.http.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface POSTENCRYPT {
    /**
     * 这里的值是配置请求的地址后缀
     * @return
     */
    String value() default "";
}
