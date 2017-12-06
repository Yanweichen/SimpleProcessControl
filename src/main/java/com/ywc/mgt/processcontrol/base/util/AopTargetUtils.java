package com.ywc.mgt.processcontrol.base.util;

import com.ywc.mgt.processcontrol.base.exception.EndRecursionException;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * AOP代理对象工具类
 * 获取AOP代理过的对象的原始对象
 *
 * @author yanweichen
 *         createTime 2017-02-16 13:02:19
 */
public class AopTargetUtils {


	/**
     * 获取 目标对象
	 * @param proxy 代理对象
	 * @return 目标对象
	 * @throws Exception
	 */
	public static Optional<Object> getTarget(Object proxy){
		if(!AopUtils.isAopProxy(proxy)) {
			return Optional.ofNullable(proxy);//不是代理对象
		}

		if(AopUtils.isJdkDynamicProxy(proxy)) {
			try {
				getJdkDynamicProxyTargetObject(proxy);
			} catch (EndRecursionException e) {
				return Optional.ofNullable(e.getObject());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else { //cglib
			try {
				getCglibProxyTargetObject(proxy);
			} catch (EndRecursionException e) {
				return Optional.ofNullable(e.getObject());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Optional.empty();
	}


	private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
		Field field = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        field.setAccessible(true);
        Object dynamicAdvisedInterceptor = field.get(proxy);
        
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        
        Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

        if(!AopUtils.isAopProxy(target)){
			throw new EndRecursionException(target);
		}
		getCglibProxyTargetObject(target);
        return null;
	}


	private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
		Field field = proxy.getClass().getSuperclass().getDeclaredField("h");
        field.setAccessible(true);
        AopProxy aopProxy = (AopProxy) field.get(proxy);
        
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        
        Object target = ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();
		if(!AopUtils.isAopProxy(target)){
			throw new EndRecursionException(target);
		}
		getJdkDynamicProxyTargetObject(target);
        return null;
	}
	
}
