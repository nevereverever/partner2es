package com.young.elasticsearch.annotation;

import java.lang.annotation.*;

/*************************
 * @author: YoungLu
 * @date: 2019/12/17 15:51
 * @description: elasticsearch id 注解,字段上面配置了这个注解的,被认为是es中的id字段
 **************************/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface ESID {
}
