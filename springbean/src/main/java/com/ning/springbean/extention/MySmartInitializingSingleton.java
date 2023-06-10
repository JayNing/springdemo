package com.ning.springbean.extention;

import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * desc: SmartInitializingSingleton
 * createBy: Ningjianjian
 *
 * 功能特性
 * 1、SmartInitializingSingleton主要用于在Spring容器启动完成时进行扩展操作，即afterSingletonsInstantiated()；
 *
 * 2、实现SmartInitializingSingleton接口的bean的作用域必须是单例，afterSingletonsInstantiated()才会触发；
 *
 * 3、afterSingletonsInstantiated()触发执行时，非懒加载的单例bean已经完成实现化、属性注入以及相关的初始化操作；
 *
 * 3、afterSingletonsInstantiated()的执行时机是在DefaultListableBeanFactory#preInstantiateSingletons()；
 * ————————————————
 *
 * 总结
 * 如果要在业务开发中使用SmartInitializingSingleton扩展点，需要特别注意实现这个接口的bean应该是非懒加载的单例bean，
 * 执行时机是在bean完成实例化、属性注入、相关初始化操作后，否则无法触发执行
 *
 * 版权声明：本文为CSDN博主「凡夫贩夫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/fox9916/article/details/129042652
 *
 */
public class MySmartInitializingSingleton implements SmartInitializingSingleton {

    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("MySmartInitializingSingleton  afterSingletonsInstantiated 执行了。。。。。");
    }
}
