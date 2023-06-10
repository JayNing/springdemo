package com.ning.springbean.factorybean;

import com.ning.springbean.model.Cat;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * desc: FactoryBean
 * createBy: Ningjianjian
 *
 * 功能特性
 * 1、FactoryBean接口有三个方法：
 *      1、getObject()，用于获取bean，主要应用在创建一些复杂的bean的场景；2、getObjectType()，用于获取返回bean的类型；3、isSingleton()，用于判断返回bean是否属于单例，默认trure，通俗说就是工厂bean；
 *
 * 2、BeanFactory是Spring bean容器的根接口，ApplicationContext是Spring bean容器的高级接口，继承于BeanFactory，通俗理解就是生成bean的工厂；
 * ————————————————
 *
 * 工作原理
 * 从单元测试验证结果来看，ComputerFactoryBean本尊在Spring容器实例化的过程中，就以非懒加载的单例bean实例化完成注册到了Spring容器里。顺着context.getBean("computerFactoryBean")往下跟踪，会发现接着调用了AbstractApplicationContext#getBean(java.lang.String)-->AbstractBeanFactory#getBean(java.lang.String)-->AbstractAutowireCapableBeanFactory#getObjectForBeanInstance()-->AbstractBeanFactory#getObjectForBeanInstance()，到这是一个关键点：
 *
 * 1、AbstractBeanFactory#getObjectForBeanInstance()中判断是否bean是否是一个工厂引用，即beanName是否是“&”开头，如果beanName是以“&”开头，则直接返回本尊；
 * 如果不是，则继续向下执行；
 *
 * 2、检查bean是否实现了FactoryBean接口，如果获取的bean没有实现FactoryBean接口，则直接返回；如果获取的bean实现了FactoryBean接口，则继续向下执行；
 *
 * 3、对获取的bean强制转换为FactoryBean，然后去执行FactoryBean接口实现类的getObject()；
 * ————————————————
 * 版权声明：本文为CSDN博主「凡夫贩夫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/fox9916/article/details/129070764
 */
@Component
public class MyFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        Cat cat = new Cat();
        cat.setName("Tom");
        cat.setAge(10);
        return cat;
    }

    @Override
    public Class<?> getObjectType() {
        return Cat.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
