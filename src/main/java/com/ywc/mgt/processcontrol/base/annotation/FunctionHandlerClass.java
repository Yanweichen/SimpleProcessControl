package com.ywc.mgt.processcontrol.base.annotation;

import java.lang.annotation.*;

/**
 * 方法执行者类注解
 *
 * @author yanwe
 *         createTime 2017-02-2017/2/15 9:16
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionHandlerClass {

    /**
     * 流程名称
     *
     * @return 流程名称
     */
    String processName();

    /**
     * 流程图ID
     *
     * @return 流程图ID
     */
    String [] bmpIds();

    /**
     * 对应需要清理的状态集合
     *
     * @return 状态集合
     */
    int [] needCleanStatus() default {-1};

}
