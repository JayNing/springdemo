package com.ning.springbean.postprocessor;

import com.ning.springbean.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 * desc: @PostConstruct
 * createBy: Ningjianjian
 *
 * 功能特性
 * 从@PostConstruct注解的注释上看，可以了解到以下内容：
 *
 * 1、要在依赖加载后，对象使用前执行，并且只执行一次；
 *
 * 2、所有支持依赖注入的类都需要支持此方法。即使类没有请求注入任何的资源，也必须调用被@PostConstruct注解标记的方法；
 *
 * 3、一个类中在一个方法上使用@PostConstruct注解；
 *
 * 4、使用@PostConstruct注解标记的方法不能有参数，除非是拦截器，可以采用拦截器规范定义的InvocationContext对象。
 *
 * 5、使用@PostConstruct注解标记的方法不能有返回值，实际上如果有返回值，也不会报错，但是会忽略掉；
 *
 * 6、使用@PostConstruct注解标记的方法的权限，public、private、protected都可以；
 *
 * 7、使用@PostConstruct注解标记的方法不能被static修饰，但是final是可以的；
 * ————————————————
 * 工作原理
 * @PostConstruct的工作原理的关键问题就是：在Spring容器启动的过程，被@PostConstruct标记的方法是怎么被执行的？
 *
 * 在被@PostConstruct标记的方法上打上断点，待程序执行的断点的时候观察一下方法调用栈信息，这时会发现：
 *
 * 1、Spring容器启动过程的最后一步，即把需要提前注册的一些非懒加载的单例Bean时，
 *  在BeanPostProcessor接口的扩展方法中，被@PostConstruct标记的方法开始触发执行，
 *  入口位置在AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization。
 * 2、那么触发被@PostConstruct注解标记的方法执行的BeanPostProcessor接口的具体是实现是哪个类呢？通过debug分析，是CommonAnnotationBeanPostProcessor类。
 * 3、CommonAnnotationBeanPostProcessor类继承于InitDestroyAnnotationBeanPostProcessor，
 *  实际的触发@PostConstruct标记方法执行的入口是在InitDestroyAnnotationBeanPostProcessor的postProcessBeforeInitialization()
 * 4、InitDestroyAnnotationBeanPostProcessor的postProcessBeforeInitialization()内，逻辑相对比较简洁，
 * 先查询bean中被@PostConstruct标记的方法，然后再使用java反射来执行这个方法
 *
 * 总结
 * 从以上几步的分析来看，被@PostConstruct标记的方法是怎么被执行的，这个问题回答清楚了。
 * 那么如果面试官问你，你了解@PostContruct注解是怎么工作的吗？
 * 你就可以这么回答他：
 * 在Bean实例化、属性注入后，被@PostConstruct标记的方法是在BeanPostProcessor的扩展方法postProcessBeforeInitialization()触发执行的，
 * 具体实现类是InitDestroyAnnotationBeanPostProcessor，
 * 具体的逻辑是：先查询被@PostConstruct标记的方法，然后使用java反射去执行这个方法。
 * 回答完后，如果他不换一个问题的话，把Springboot的扩展点都给他盘一遍。
 * ————————————————
 * 版权声明：本文为CSDN博主「凡夫贩夫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/fox9916/article/details/128998846
 */
@Component
public class PostConstructDemo {

    private UserService userService;

    public PostConstructDemo() {
        System.out.println("PostConstructDemo 无参构造方法执行了。。。。。。");
    }

    public UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        System.out.println("PostConstructDemo 中 userService 自动注入，执行了。。。。。。");
        this.userService = userService;
    }

    @PostConstruct
    public void myPostConstruct(){
        System.out.println("PostConstructDemo  @PostConstruct 方法执行了。。。。。。");
    }

    @PostConstruct
    public void myPostConstruct2(){
        System.out.println("PostConstructDemo  @PostConstruct2 方法执行了。。。。。。");
    }
}
