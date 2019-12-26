package com.young.elasticsearch.annotation;

import java.lang.annotation.*;

/*************************
 * @author: YoungLu
 * @date: 2019/12/19 14:37
 * @description: es字段标识
 **************************/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface ESField {
    /**
     * 字段值
     */
    String value() default "";

    /**
     * 是否为es字段
     * 默认 true 存在，false 不存在
     */
    boolean exist() default true;
}
