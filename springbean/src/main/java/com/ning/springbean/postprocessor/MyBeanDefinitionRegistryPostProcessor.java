package com.ning.springbean.postprocessor;

import com.ning.springbean.model.Dog;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * desc:
 * createBy: Ningjianjian
 *
 * BeanDefinitionRegistryPostProcessor作用
 * 在Spring容器初始后、未刷新前，即Bean已被扫描注册为BeanDefinition后，未正式实例化前，可以通过实现BeanDefinitionRegistryPostProcessor做一些额外的操作.
 *
 * 使用案例：
 * 新的bean，可以通过这个方式，自己通过postProcessBeanDefinitionRegistry新建BeanDefinition，放到IOC容器中进行bean管理。这样就不需要@Bean或XML配置方式了
 * 一些自定义注解，被自定义注解标记的类，可以通过这个方式，生成beanDefinition，注入到IOC容器，被IOC管理起来。例如：Mybatis的@Mapper注解
 *
 * 初始化和执行时机
 *
 * 通过自定义的MyBeanDefinitionRegistryPostProcessor类，实现BeanDefinitionRegistryPostProcessor接口，从项目启动开始，其执行过程如下：
 *
 * 1、执行项目的主类，org.springframework.boot.SpringApplication#run被调用；
 *
 * 2、进入boot.SpringApplication#run方法后，刚开始是一些Spring容器初始化的配置操作，直到执行到org.springframework.boot.SpringApplication#refreshContext，开始容器刷新，进入了关键阶段；
 *
 * 3、在SpringApplication#refreshContext，实际的刷新逻辑是在org.springframework.context.support.AbstractApplicationContext#refresh方法中；
 *
 * 4、AbstractApplicationContext#refresh方法中，调用org.springframework.context.support.AbstractApplicationContext#invokeBeanFactoryPostProcessors开始初始化
 * 和执行实现BeanDefinitionRegistryPostProcessor接口的postProcessBeanDefinitionRegistry()和postProcessBeanFactory()；
 *
 * 5、进入AbstractApplicationContext#invokeBeanFactoryPostProcessors方法，
 * 发现又调用了org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors()；
 *
 * 6、在PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors()方法中，并不是直接就初始化和执行postProcessBeanDefinitionRegistry()和postProcessBeanFactory()，
 * 而是又进行了一系列的判断，其判断顺序是：1、通过AbstractApplicationContext#addBeanFactoryPostProcessor提前注册的BeanDefinitionRegistryPostProcessor实现类；
 * 2、实现了PriorityOrdered接口；3、是否实现了Ordered；4、剩下的其他BeanDefinitionRegistryPostProcessor实现类；自定义的MyBeanDefinitionRegistryPostProcessor就属于第4类，
 * 所以是所有实现里较晚才被执行的，如果想要提前被执行，可以考虑前面三种方式；
 *
 * 7、在PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors()方法中执行完MyBeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry方法后，
 * 紧接着就开始执行MyBeanDefinitionRegistryPostProcessor#postProcessBeanFactory方法了；从整个调用过程看postProcessBeanDefinitionRegistry()是
 * 早于postProcessBeanFactory()方法执行；
 * ————————————————
 * 版权声明：本文为CSDN博主「凡夫贩夫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/fox9916/article/details/128538625
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Dog.class);

        MutablePropertyValues propertyValues = new MutablePropertyValues();

        PropertyValue propertyValue1 = new PropertyValue("name", "老大");
        PropertyValue propertyValue2 = new PropertyValue("age", 3);

        propertyValues.addPropertyValue(propertyValue1);
        propertyValues.addPropertyValue(propertyValue2);

        rootBeanDefinition.setPropertyValues(propertyValues);

        registry.registerBeanDefinition("dog", rootBeanDefinition);

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition dog = beanFactory.getBeanDefinition("dog");
        MutablePropertyValues propertyValues = dog.getPropertyValues();

        List<PropertyValue> propertyValueList = propertyValues.getPropertyValueList();
        for (PropertyValue propertyValue : propertyValueList) {
            System.out.println(propertyValue.getName() + ":" + propertyValue.getValue());
        }
    }
}
