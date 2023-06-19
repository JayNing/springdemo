package com.ning.springbean.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * BeanFactoryPostProcessor的执行是Spring Bean生命周期非常重要的一部分；
 *
 * BeanFactory级别的后置处理器，在Spring生命周期内，org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory只会执行一次；
 *
 * 允许在容器读取到Bean的BeanDefinition数据之后，bean未实例化前，读取BeanDefiniion数据，并且可以根据需要进行修改；
 * ————————————————
 * 版权声明：本文为CSDN博主「凡夫贩夫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/fox9916/article/details/128823328
 */

/**
 * 应用场景
 * 对敏感信息的解密处理，比如数据库连接信息加密和解密：在实际的业务开发中，在配置文件中明文配置mysq，redis的密码实际上是不安全的，需要配置加密后的密码信息；
 * 但是把加密后的密码信息注入的数据源中，去连接mysql数据库肯定会连接异常，因为mysql并不知道你的加密方式和加密方法。
 * 这就会产生一个需求：需要在配置文件中配置的数据库信息是加密的，但是在把密码信息注入数据源前在程序里解密处理。
 *
 * BeanFactoryPostProcessor正好可以解决这个问题，在真正使用到数据源去连接数据库前，读取到加密信息，进行解密处理，再用解密后的信息替换掉Spring容器中加密信息。
 * ————————————————
 * 版权声明：本文为CSDN博主「凡夫贩夫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/fox9916/article/details/128823328
 */
/**
 * desc:
 * @author: Ningjianjian
 * @date 2023/06/01
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostProcessor() {
        super();
        System.out.println("这是BeanFactoryPostProcessor实现类构造器！！");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0)
            throws BeansException {
        System.out
                .println("BeanFactoryPostProcessor调用postProcessBeanFactory方法");
        BeanDefinition bd = arg0.getBeanDefinition("person");
        bd.getPropertyValues().addPropertyValue("phone", "110");
        bd.getPropertyValues().addPropertyValue("address", "苏州姑苏区图书馆");
        bd.getPropertyValues().addPropertyValue("dbPassword", "数据库密码");
    }

}
