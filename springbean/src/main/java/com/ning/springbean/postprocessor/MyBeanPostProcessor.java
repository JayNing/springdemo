package com.ning.springbean.postprocessor;

import com.ning.springbean.model.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * desc: BeanPostProcessor接口包括2个方法postProcessAfterInitialization和postProcessBeforeInitialization，
 * 这两个方法的第一个参数都是要处理的Bean对象，第二个参数都是Bean的name。返回值也都是要处理的Bean对象。这里要注意
 * createBy: Ningjianjian
 *
 * 功能特性
 * 1、BeanPostProcessor是Bean级别的扩展接口，在Spring管理的Bean实例化完成后，预留了两种扩展点；
 *
 * 2、这两处扩展的实现方式就是实现BeanPostProcessor接口，并将实现类注册到Spring容器中；
 *
 * 3、两种扩展点分别是BeanPostProcessor接口的postProcessBeforeInitialization方法和postProcessAfterInitialization方法；
 *
 * 4、postProcessBeforeInitialization方法的执行时机是在Spring管理的Bean实例化、属性注入完成后，InitializingBean#afterPropertiesSet方法以及自定义的初始化方法之前；
 *
 * 5、postProcessAfterInitialization方法的执行时机是在InitializingBean#afterPropertiesSet方法以及自定义的初始化方法之后；
 *
 * 6、BeanPostProcessor接口的实现类的postProcessBeforeInitialization方法和postProcessAfterInitialization方法，在Spring管理的每个bean初始化前后都会执行到；
 * ————————————————
 *
 * 从单元测试的执行结果来看，验证了Spring的扩展点BeanPostProcessor的执行时机，
 * 即
 * postProcessBeforeInitialization方法的执行时机是在Spring管理的Bean实例化、属性注入完成后，InitializingBean#afterPropertiesSet方法以及自定义的初始化方法之前；
 * postProcessAfterInitialization方法的执行时机是在InitializingBean#afterPropertiesSet方法以及自定义的初始化方法之前之后；
 * ————————————————
 * 一、注册时机
 * 1、BeanPostProcessor中的两个扩展方法中，postProcessBeforeInitialization方法是先被执行的，即Bean实例化和属性注入完成之后，通过实现方式示例代码的Debug，
 * 找到了BeanPostProcessor接口的实现类到Spring容器中的入口，即org.springframework.context.support.AbstractApplicationContext#refresh--->registerBeanPostProcessors
 *
 * BeanPostProcessor的注册时机是在Spring容器启动过程中，即BeanFactoryPostProcessor扩展点的逻辑执行完成后，紧接着就开始了BeanPostProcessor的注册，
 * 其具体的注册逻辑在PostProcessorRegistrationDelegate#registerBeanPostProcessors()
 * ————————————————
 *
 * 二、BeanPostProcessor的实现类的postProcessBeforeInitialization方法和postProcessAfterInitialization方法是如何被执行的
 * 1、org.springframework.context.support.AbstractApplicationContext#refresh--->finishBeanFactoryInitialization()
 * 2、--->DefaultListableBeanFactory#preInstantiateSingletons--->getBean()
 * 3、--->AbstractAutowireCapableBeanFactory#doCreateBean ---> initializeBean()
 * 4、进入到initializeBean()一看，判断的果然没错，
 * 先执行BeanPostProcessor接口实现类的postProcessBeforeInitialization方法【注意，在执行postProcessBeforeInitialization之前，还是先执行了Aware接口的注入，invokeAwareMethods()】，
 * 接着如果bean实现了InitializingBean或者自定义了initMethod，就会在这里执行InitializingBean#afterPropertiesSet和initMethod方法，
 * 最后会执行执行BeanPostProcessor接口实现类的postProcessAfterInitialization方法
 * ————————————————
 * 应用场景
 * 处理自定义注解
 *         在程序中我们可以自定义注解并标到相应的类上，当个类注册到Spring容器中，并实例化完成后，希望触发自定义注解对应的一些其他操作的时候，就可以通过BeanPostProcessor来实现。
 *
 * 参数校验
 *         Springboot参数校验具体实现方式，其核心原理正是用到了BeanPostProcessor扩展点，
 *         具体的实现类是org.springframework.validation.beanvalidation.BeanValidationPostProcessor
 * ————————————————
 * 版权声明：本文为CSDN博主「凡夫贩夫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/fox9916/article/details/128917992
 *
 *
 *
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        super();
        System.out.println("这是BeanPostProcessor实现类构造器！！");
        // TODO Auto-generated constructor stub
    }

    @Override
    public Object postProcessAfterInitialization(Object arg0, String arg1)
            throws BeansException {
        if (arg0 instanceof Person) {
            System.out
                    .println("BeanPostProcessor接口方法postProcessAfterInitialization对属性进行更改！");
        }
        return arg0;
    }

    @Override
    public Object postProcessBeforeInitialization(Object arg0, String arg1)
            throws BeansException {
        if (arg0 instanceof Person) {
            System.out
                    .println("BeanPostProcessor接口方法postProcessBeforeInitialization对属性进行更改！");
        }
        return arg0;
    }

}
