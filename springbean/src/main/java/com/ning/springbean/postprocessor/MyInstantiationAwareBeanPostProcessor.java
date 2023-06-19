package com.ning.springbean.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import java.beans.PropertyDescriptor;

/**
 * desc: InstantiationAwareBeanPostProcessor接口
 * createBy: Ningjianjian
 * 接口继承链路：
 * InstantiationAwareBeanPostProcessorAdapter -> SmartInstantiationAwareBeanPostProcessor -> InstantiationAwareBeanPostProcessor -> BeanPostProcessor
 *
 *功能特性
 * 1、虽然InstantiationAwareBeanPostProcessor继承于BeanPostProcessor，但是InstantiationAwareBeanPostProcessor的执行时机要稍早于BeanPostProcessor；
 *
 * 2、InstantiationAwareBeanPostProcessor有三个扩展方法，分别是：postProcessBeforeInstantiation()、postProcessAfterInstantiation()、postProcessProperties()；
 *
 * 3、postProcessBeforeInstantiation()在Spring中Bean实例化前触发执行；
 *
 * 4、postProcessAfterInstantiation()、postProcessProperties()在Spring中Bean实例化后，属性注入前触发执行；
 *
 * 5、InstantiationAwareBeanPostProcessor继承于BeanPostProcessor的postProcessBeforeInitialization()和postProcessAfterInitialization()则在Spring中Bean实例化、属性注入完成后触发执行；
 *
 * 6、postProcessBeforeInstantiation()扩展点可以自定义个性化的Bean来替换掉目标Bean，需要注意的是替换掉目标Bean后，postProcessAfterInstantiation()会执行，其他的扩展点将不再触发；
 *
 * 7、postProcessAfterInstantiation()的返回值为布尔类型，如果返回值为true，则第三个扩展点postProcessProperties()会继续执行；如果返回值为false，则第三个扩展点postProcessProperties()将不再执行；
 *
 * 8、postProcessProperties()扩展点可以在目标Bean实例化后，属性注入前，对要注入的属性值内容进行更改，以替换掉原来的属性值；
 * ————————————————
 * 注册时机
 * 1、因为InstantiationAwareBeanPostProcessor接口继承于BeanPostProcessor接口，所以InstantiationAwareBeanPostProcessor接口的实现类的注册时机和BeanPostProcessor是一致的，
 * 因此很快就找到了InstantiationAwareBeanPostProcessor接口的实现类的注册入口，
 * 即org.springframework.context.support.AbstractApplicationContext#refresh--->registerBeanPostProcessors
 * ————————————————
 * 执行时机
 *
 * 1、org.springframework.context.support.AbstractApplicationContext#refresh--->finishBeanFactoryInitialization()
 *  * 2、--->DefaultListableBeanFactory#preInstantiateSingletons--->getBean() -->AbstractAutowireCapableBeanFactory#createBean()
 *  3、 --->AbstractAutowireCapableBeanFactory#resolveBeforeInstantiation() --> postProcessBeforeInstantiation()
 *
 *  4、AbstractAutowireCapableBeanFactory#doCreateBean---> populateBean() --> postProcessAfterInstantiation()
 *  --> 这里要注意一下返回值的类型为布尔类型：如果返回true，则会继续执行第三个扩展点；如果返回fase，则第三个扩展点postProcessProperties()不会执行，直接return跳出了当前方法；
 *  5、还是在AbstractAutowireCapableBeanFactory#populateBean方法中，第二个扩展点执行过且返回值为true，接着往下就会执行到第三个扩展点postProcessProperties()
 *
 *
 * 版权声明：本文为CSDN博主「凡夫贩夫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/fox9916/article/details/128941464
 *
 */
public class MyInstantiationAwareBeanPostProcessor extends
        InstantiationAwareBeanPostProcessorAdapter {

    public MyInstantiationAwareBeanPostProcessor() {
        super();
        System.out
                .println("这是InstantiationAwareBeanPostProcessorAdapter实现类构造器！！");
    }

    // 接口方法、实例化Bean之前调用
    @Override
    public Object postProcessBeforeInstantiation(Class beanClass,
                                                 String beanName) throws BeansException {
        if ("person".equals(beanName)) {
            System.out
                    .println("InstantiationAwareBeanPostProcessor调用postProcessBeforeInstantiation方法");
        }
        return null;
    }

    // 接口方法、实例化Bean之后调用
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if ("person".equals(beanName)) {
            System.out
                    .println("InstantiationAwareBeanPostProcessor调用postProcessAfterInstantiation方法");
        }
        return true;
    }

    // 接口方法、初始化Bean之前调用
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("person".equals(beanName)) {
            System.out
                    .println("InstantiationAwareBeanPostProcessor调用postProcessBeforeInitialization方法");
        }
        return bean;
    }

    // 接口方法、初始化Bean之后调用
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if ("person".equals(beanName)) {
            System.out
                    .println("InstantiationAwareBeanPostProcessor调用postProcessAfterInitialization方法");
        }
        return bean;
    }

    // 接口方法、设置某个属性时调用
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs,
                                                    PropertyDescriptor[] pds, Object bean, String beanName)
            throws BeansException {
        if ("person".equals(beanName)) {
            System.out
                    .println("InstantiationAwareBeanPostProcessor调用postProcessPropertyValues方法");
        }
        return pvs;
    }
}