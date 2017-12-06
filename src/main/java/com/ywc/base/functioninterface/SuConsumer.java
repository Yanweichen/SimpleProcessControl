package com.ywc.base.functioninterface;

/**
 * 三参数接口
 *
 * @author yanwe
 *         createTime 2017-03-2017/3/10 10:06
 */
@FunctionalInterface
public interface SuConsumer<T, U, V> {

    void apply(T t, U u, V v) throws Exception;
}
