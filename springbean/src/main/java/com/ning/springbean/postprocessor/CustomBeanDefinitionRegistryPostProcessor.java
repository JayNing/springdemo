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
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * desc: 自定义BeanDefinitionRegistryPostProcessor，
 *  用于测试通过扩展ApplicationContextInitializer，注入BeanDefinitionRegistryPostProcessor
 * createBy: Ningjianjian
 *
 *
 */
public class CustomBeanDefinitionRegistryPostProcessor implements PriorityOrdered, BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        System.out.println("CustomBeanDefinitionRegistryPostProcessor  postProcessBeanDefinitionRegistry 触发执行了。。。 ");

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("CustomBeanDefinitionRegistryPostProcessor  postProcessBeanFactory 触发执行了。。。 ");
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 1;
    }
}
