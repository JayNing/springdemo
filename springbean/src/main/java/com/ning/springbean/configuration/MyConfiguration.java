package com.ning.springbean.configuration;

import com.ning.springbean.destroy.FishDisposableBean;
import com.ning.springbean.extention.MySmartInitializingSingleton;
import com.ning.springbean.postprocessor.MyBeanFactoryPostProcessor;
import com.ning.springbean.postprocessor.MyBeanPostProcessor;
import com.ning.springbean.postprocessor.MyInitializingBean;
import com.ning.springbean.postprocessor.MyInstantiationAwareBeanPostProcessor;
import com.ning.springbean.model.Person;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * desc:
 * createBy: Ningjianjian
 */
@Configuration
public class MyConfiguration {

    @Bean(initMethod = "myInit", destroyMethod = "myDestory")
    public Person person(){
        return new Person();
    }

    @Bean
    public FishDisposableBean fishDisposableBean(){
        return new FishDisposableBean();
    }
//
//    @Bean
//    public MyBeanPostProcessor beanPostProcessor(){
//        return new MyBeanPostProcessor();
//    }

    @Bean(initMethod = "initMethod")
    public MyInitializingBean myInitializingBean(){
        return new MyInitializingBean();
    }

    @Bean
    public MySmartInitializingSingleton mySmartInitializingSingleton(){
        return new MySmartInitializingSingleton();
    }

    @Bean
    public MyInstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor(){
        return new MyInstantiationAwareBeanPostProcessor();
    }
    @Bean
    public MyBeanFactoryPostProcessor beanFactoryPostProcessor(){
        return new MyBeanFactoryPostProcessor();
    }



}
