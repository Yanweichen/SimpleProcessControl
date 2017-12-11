package com.ywc.mgt.processcontrol.base.annotation;

import java.lang.annotation.*;

/**
 * 方法执行者方法注解
 *
 * @author yanwe
 *         createTime 2017-02-2017/2/15 9:16
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionHandlerMethod {
}
