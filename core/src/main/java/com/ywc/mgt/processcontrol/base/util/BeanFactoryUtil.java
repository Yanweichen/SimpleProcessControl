package com.ywc.mgt.processcontrol.base.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * bean 信息获取和修改
 *
 * @author johnmyiqn
 */
@Component
public class BeanFactoryUtil implements BeanFactoryPostProcessor {

    private static ConfigurableListableBeanFactory beanFactory; // Spring应用上下文环境

    /**
     * 创建bean工厂对象，bean初始化之前
     *
     * @param beanFactory bean工厂对象
     * @throws BeansException 创建工厂失败异常
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 获取bean实例对象
     *
     * @param name bean名称
     * @param <T>  泛型类
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException 获取bean失败异常
     */
    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param classZ 类类型
     * @param <T>    泛型类
     * @return classZ类型bean实例
     * @throws BeansException 获取bean失败异常
     */
    public static <T> T getBean(Class<T> classZ) throws BeansException {
        T result = beanFactory.getBean(classZ);
        return result;
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name bean实体名称
     * @return true存在bean，false不存在bean
     */
    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name bena名称
     * @return true是单例，false不是单例
     * @throws NoSuchBeanDefinitionException bean未定义异常
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    /**
     * @param name bean名称
     * @return Class 注册对象的类型
     * @throws NoSuchBeanDefinitionException bean未定义异常
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name bean名称
     * @return bean别名列表
     * @throws NoSuchBeanDefinitionException bean未定义异常
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

    /**
     * 根据注解获得Bean
     *
     * @return bean别名列表
     * @throws NoSuchBeanDefinitionException bean未定义异常
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws NoSuchBeanDefinitionException {
        return beanFactory.getBeansWithAnnotation(annotationType);
    }

    /**
     * 根据父类获得Bean
     *
     * @return bean别名列表
     * @throws NoSuchBeanDefinitionException bean未定义异常
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> bean) throws NoSuchBeanDefinitionException {
        return beanFactory.getBeansOfType(bean);
    }

}
