package com.ning.springbean.model;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * desc:
 * createBy: Ningjianjian
 */
@Data
public class Person implements BeanFactoryAware, BeanNameAware,
        InitializingBean, DisposableBean, ApplicationContextAware, SmartInitializingSingleton {

    private String name;
    private String address;
    /**
     * 数据库链接的密码，比如在配置文件中，存储的加密的密文，但是后台链接数据库使用的时候，
     * 是需要进行解密，替换成正确的数据库密码使用的，需要设置成正确的密码。
     * 可以使用BeanFactoryPostProcess.postProcessBeanFactory()方法进行设置
     */
    private String dbPassword;

    private int phone;

    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;
    private String beanName;

    @Autowired
    private Dog dog;

    public Person() {
        System.out.println("【构造器】调用Person的构造器实例化");
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        System.out.println("【注入属性】注入属性dbPassword， 加密后的数据库密码");
        this.dbPassword = dbPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("【注入属性】注入属性name");
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        System.out.println("【注入属性】注入属性address");
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        System.out.println("【注入属性】注入属性phone");
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Person [address=" + address + ", name=" + name + ", phone="
                + phone + ", dbPassword=" + dbPassword + "]";
    }

    // 这是BeanFactoryAware接口方法
    @Override
    public void setBeanFactory(BeanFactory arg0) throws BeansException {
        System.out
                .println("【BeanFactoryAware接口】调用BeanFactoryAware.setBeanFactory()");
        this.beanFactory = arg0;
    }

    // 这是BeanNameAware接口方法
    @Override
    public void setBeanName(String arg0) {
        System.out.println("【BeanNameAware接口】调用BeanNameAware.setBeanName()");
        this.beanName = arg0;
    }

    // 这是InitializingBean接口方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out
                .println("【InitializingBean接口】调用InitializingBean.afterPropertiesSet()");
    }

    // 这是DiposibleBean接口方法
    @Override
    public void destroy() throws Exception {
        System.out.println("【DiposibleBean接口】调用DiposibleBean.destory()");
    }

    // 通过<bean>的init-method属性指定的初始化方法
    public void myInit() {
        System.out.println("【init-method】调用<bean>的init-method属性指定的初始化方法");
    }

    // 通过<bean>的destroy-method属性指定的初始化方法
    public void myDestory() {
        System.out.println("【destroy-method】调用<bean>的destroy-method属性指定的初始化方法");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out
                .println("【ApplicationContextAware接口】调用ApplicationContextAware.setApplicationContext()");
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        System.out
                .println("【SmartInitializingSingleton接口】调用SmartInitializingSingleton.afterSingletonsInstantiated()");
    }
}