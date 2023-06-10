package com.ning.springbean.aware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * desc: ApplicationContextAwareProcessor 功能介绍
 * createBy: Ningjianjian
 *
 * 功能特性
 * ApplicationContextAwareProcessor本身并不是扩展点，而是实现了BeanPostProcessor，并实现postProcessBeforeInitialization()，
 * 所以并不需要去实现它，但是其内部包含了以下6个接口实现的执行时机，这几个接口的功能作用分别是：
 *
 * 1、EnvironmentAware：用于获取Enviroment，Enviroment可以获得系统内的所有参数；另外也可以通过注入的方式来获得Environment，用哪种方式需要以实现场景而决定。
 *
 * 2、EmbeddedValueResolverAware：用于获取StringValueResolver，StringValueResolver可以获取基于String类型的properties的变量；
 * 另外还可以使用@Value的方式来获取properties的变量，用哪种方式需要以实现场景而决定。
 *
 * 3、ResourceLoaderAware：用于获取ResourceLoader，ResourceLoader可以用于获取classpath内所有的资源对象。
 *
 * 4、ApplicationEventPublisherAware：用于获取ApplicationEventPublisher，ApplicationEventPublisher可以用来发布事件，
 * 当然这个对象也可以通过spring注入的方式来获得，具体的实现方式可以参考Springboot事件监听机制的实战应用。
 *
 * 5、MessageSourceAware：用于获取MessageSource，MessageSource主要用来做国际化。
 *
 * 6、ApplicationContextAware：用来获取ApplicationContext，ApplicationContext就是Spring上下文管理器。
 * ————————————————
 *
 * 注册时机
 * ApplicationContextAwareProcessor的注册时机，即准备BeanFactory的时候，
 * 注册的入口在AbstractApplicationContext#refresh----->AbstractApplicationContext#prepareBeanFactory方法中。
 * ————————————————
 *
 * 总结
 *
 * 1、ApplicationContextAwareProcessor实现BeanPostProcessor接口，是Spring扩展点之BeanPostProcessor的内部经典实现。
 *
 * 2、ApplicationContextAwareProcessor#postProcessBeforeInitialization内部逻辑很简单，主要是执行了XxxAware相关扩展接口具体实现；
 *
 * 3、ApplicationContextAwareProcessor注册时机相对比较早，即BeanFactory实例化后，相关属性初始化时；
 *
 * 4、ApplicationContextAwareProcessor#postProcessBeforeInitialization的执行时机，
 * 是在Spring管理的Bean实例化、属性注入完成后，InitializingBean#afterPropertiesSet方法以及自定义的初始化方法之前；
 * ————————————————
 * 版权声明：本文为CSDN博主「凡夫贩夫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/fox9916/article/details/128990305
 *
 */
@Component
public class MyApplicationContextAware implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 可以用来提供获取bean的工具类，SpringBeanUtil.java
     * @param beanName
     * @return
     */
    public Object getBeanByName(String beanName){
        return this.applicationContext.getBean(beanName);
    }


    public Object getBeanByClass(Class  clazz){
        return this.applicationContext.getBean(clazz);
    }


}
