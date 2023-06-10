package com.ning.springbean.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;

/**
 * desc: SmartInstantiationAwareBeanPostProcessor接口
 * createBy: Ningjianjian
 * 接口继承链路：
 * SmartInstantiationAwareBeanPostProcessor -> InstantiationAwareBeanPostProcessor -> BeanPostProcessor
 *
 *功能特性
 * 1、SmartInstantiationAwareBeanPostProcesso接口有三个扩展点，分别是predictBeanType()、determineCandidateConstructors()、getEarlyBeanReference()；
 *
 * 2、predictBeanType()用于在Bean实例化前预测最终返回的Class类型，触发时机是在InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation()之前，
 * 如果不能预测，则返回null或正确的Class；如果预测一个错误的Class，那么程序就会报错了（举个例子：如果形参beanClass是Student.class，返回时改成了Teacher.class，
 * 报错是毫无疑问的；那么问题来了：这么预测的意义在哪？这一点我没有太明白，有知道的小伙伴告诉我一下哈）；
 *
 * 3、determineCandidateConstructors()决定使用哪个构造器构造Bean，如果不指定，默认为null，即bean的无参构造方法；（感觉这个扩展点意义不大，实际上构造器分为两类：
 * 无参构造方法，有参数构造方法，无非有参数的构造方法参数至少一个以上，用哪种构造器来构造Bean我感觉区别不大，打个比方：用普通碗吃饭与用金碗吃饭，对一些人来说可能是有区别的，
 * 对我来说，吃饱就好了，什么碗我不在乎，哈哈，小伙们你觉得呢？）
 *
 * 4、getEarlyBeanReference()获得提前暴露的bean引用，主要用于Spring循环依赖问题的解决，如果Spring中检测不到循环依赖，这个方法不会被调用；当存在Spring循环依赖这种情况时，
 * 会在InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation方法触发执行之后执行
 * ————————————————
 * 版权声明：本文为CSDN博主「凡夫贩夫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/fox9916/article/details/128976944
 *
 */
@Component
public class MySmartInstantiationAwareBeanPostProcessor implements
        SmartInstantiationAwareBeanPostProcessor {

    public MySmartInstantiationAwareBeanPostProcessor() {
        super();
        System.out
                .println("这是MySmartInstantiationAwareBeanPostProcessor实现类构造器！！");
    }

    // 接口方法、实例化Bean之前调用
    @Override
    public Object postProcessBeforeInstantiation(Class beanClass,
                                                 String beanName) throws BeansException {
        if (beanName.equals("person")) {
            System.out
                    .println("SmartInstantiationAwareBeanPostProcessor调用postProcessBeforeInstantiation方法");
        }
        return null;
    }

    // 接口方法、实例化Bean之后调用
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (beanName.equals("person")) {
            System.out
                    .println("SmartInstantiationAwareBeanPostProcessor调用postProcessAfterInstantiation方法");
        }
        return true;
    }

    // 接口方法、初始化Bean之前调用
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("person")) {
            System.out
                    .println("SmartInstantiationAwareBeanPostProcessor调用postProcessBeforeInitialization方法");
        }
        return bean;
    }

    // 接口方法、初始化Bean之后调用
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (beanName.equals("person")) {
            System.out
                    .println("SmartInstantiationAwareBeanPostProcessor 调用postProcessAfterInitialization方法");
        }
        return bean;
    }

    // 接口方法、设置某个属性时调用
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs,
                                                    PropertyDescriptor[] pds, Object bean, String beanName)
            throws BeansException {
        if (beanName.equals("person")) {
            System.out
                    .println("SmartInstantiationAwareBeanPostProcessor 调用postProcessPropertyValues方法");
        }
        return pvs;
    }

    /**
     * 获得提前暴露的bean引用，主要用于Spring循环依赖问题的解决，如果Spring中检测不到循环依赖，这个方法不会被调用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        if (beanName.equals("person")) {
            System.out
                    .println("SmartInstantiationAwareBeanPostProcessor 调用 getEarlyBeanReference 方法");
        }
        return bean;
    }
}