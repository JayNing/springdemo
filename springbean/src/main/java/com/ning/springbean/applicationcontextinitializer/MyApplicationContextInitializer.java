package com.ning.springbean.applicationcontextinitializer;

import com.ning.springbean.postprocessor.CustomBeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * desc: ApplicationContextInitializer
 * createBy: Ningjianjian
 *
 * ApplicationContextInitializer的功能作用
 * 1、在Spring容器初始化开始的时候，ApplicationContextInitializer接口的所有实现在类会被实例化；
 * 2、在Spring容器刷新前，所有实现类的org.springframework.context.ApplicationContextInitializer#initialize方法会被调用，
 * initialize方法的形参类型是ConfigurableApplicationContext，因此可以认为ApplicationContextInitializer实际上是Spring容器初始化
 * 前ConfigurableApplicationContext的回调接口，可以对上下文环境作一些操作，如运行环境属性注册、激活配置文件等
 * ————————————————
 *
 * Springboot的扩展点ApplicationContextInitializer接口的实现主要分为两步：
 *
 * 1、实现ApplicationContextInitializer接口；
 *
 *         MyApplicationContextInitializer实现ApplicationContextInitializer接口，并打印一下系统相关属性的key、value
 *  2、把实现类注册到Spring容器中
 *
 * 把实现类MyApplicationContextInitializer注册到Spring容器中，主要有三种方式：【注意：如果三种方式同时添加，则initialize()会执行3遍】
 * （1）spring.factories
 *         在resources目录新建/META-INF/spring.factories文件，并预置以下内容，即可完成自定义MyApplicationContextInitializer的注册；
 *          org.springframework.context.ApplicationContextInitializer=com.fanfu.config.MyApplicationContextInitializer
 *  （2）application.properties
 *    在application.properties文件中预置以下配置内容，即可完成自定义MyApplicationContextInitializer的注册；
 *     context.initializer.classes=com.fanfu.config.MyApplicationContextInitializer
 *  （3）springApplication.addInitializers()
 *     在springboot的启动类中，使用springApplication.addInitializers(new MyApplicationContextInitializer())，
 *     完成自定义MyApplicationContextInitializer的注册；
 *
 *     @SpringBootApplication
     * public class FanfuApplication {
     *     public static void main(String[] args) {
     *         SpringApplication springApplication = new SpringApplication(FanfuApplication.class);
     *         springApplication.addInitializers(new MyApplicationContextInitializer());
     *         springApplication.run(args);
     *     }
     * }
 * ————————————————
 *
 * 总结
 * 通过这篇文章，可以了解到：
 *
 * 第一，在Spring容器被刷新前，可以通过实现ApplicationContextInitializer接口对Spring上下文环境作一些配置或操作；
 *
 * 第二，ApplicationContextInitializer接口的实现方式有三种，可以根据项目需要选择合适的；
 *
 * 第三，了解了ApplicationContextInitializer接口实现类的初始化时机和执行时机，以及Springboot内置的具体实现类，
 * 可以学习到spring本身是如何利用扩展接口实现一些功能的，对实际的项目开发具有一定的参考意义
 *
 * 版权声明：本文为CSDN博主「凡夫贩夫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/fox9916/article/details/128522598
 */
@Component
public class MyApplicationContextInitializer implements ApplicationContextInitializer {

    /**
     * 本例中，参考ConfigurationWarningsApplicationContextInitializer的实现逻辑， 实现提前设置自定义的 BeanDefinitionRegistryPostProcessor 到上下文中
     * @param applicationContext
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.addBeanFactoryPostProcessor(
                new CustomBeanDefinitionRegistryPostProcessor());
    }
}
