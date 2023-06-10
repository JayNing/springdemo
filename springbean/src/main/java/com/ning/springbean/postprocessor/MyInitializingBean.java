package com.ning.springbean.postprocessor;

import org.springframework.beans.factory.InitializingBean;

import java.time.LocalDateTime;

/**
 * desc: InitializingBean
 * createBy: Ningjianjian
 *
 * 功能特性
 * 1、Spring中提供了InitializingBean接口，帮助用户实现一些自定义的初始化操作；
 *
 * 在bean实例化、属性注入后的提供了一个扩展方法afterPropertiesSet()；
 *
 * 2、其实现方式很简单，需要bean实现InitializingBean接口并且重写afterPropertiesSet()，且bean要注册到Spring容器中，
 *    那么bean在实例化、属性注入后，重写的afterPropertiesSet()就会触发执行；
 *
 * 3、与InitializingBean#afterPropertiesSet()类似效果的是init-method，但是需要注意的是InitializingBean#afterPropertiesSet()
 *    执行时机要略早于init-method；
 *
 * 4、InitializingBean#afterPropertiesSet()的调用方式是在bean初始化过程中真接调用bean的afterPropertiesSet()；
 *
 * 5、bean自定义属性init-method是通过java反射的方式进行调用 ；
 *
 * 6、InitializingBean#afterPropertiesSet()与init-method的执行时机是在BeanPostProcessor#postProcessBeforeInitialization()
 *    和BeanPostProcessor#postProcessAfterInitialization()之间；
 * ————————————————
 * 版权声明：本文为CSDN博主「凡夫贩夫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/fox9916/article/details/129016083
 */
public class MyInitializingBean implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("MyInitializingBean afterPropertiesSet is called. " + LocalDateTime.now());
    }

    public void initMethod(){
        System.out.println("MyInitializingBean init-method is called. " + LocalDateTime.now());
    }
}
