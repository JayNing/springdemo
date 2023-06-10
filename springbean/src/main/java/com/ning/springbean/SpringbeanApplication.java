package com.ning.springbean;

import com.ning.springbean.applicationcontextinitializer.MyApplicationContextInitializer;
import com.ning.springbean.aware.MyApplicationContextAware;
import com.ning.springbean.factorybean.MyFactoryBean;
import com.ning.springbean.model.Cat;
import com.ning.springbean.model.Dog;
import com.ning.springbean.model.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringbeanApplication {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(SpringbeanApplication.class);

//        springApplication.addInitializers(new MyApplicationContextInitializer());

        ConfigurableApplicationContext applicationContext = springApplication.run(args);

        System.out.println("现在开始初始化容器");

        System.out.println("容器初始化成功");
        //得到Person，并使用
        Person person = applicationContext.getBean("person",Person.class);
        System.out.println(person);

        Dog dog = applicationContext.getBean("dog", Dog.class);
        System.out.println(dog);

        System.out.println("========");

        MyFactoryBean myFactoryBean = (MyFactoryBean) applicationContext.getBean("&myFactoryBean");
        Cat cat = (Cat) applicationContext.getBean("myFactoryBean");
        System.out.println("myFactoryBean : " + myFactoryBean);
        System.out.println(cat);


        MyApplicationContextAware myApplicationContextAware = (MyApplicationContextAware) applicationContext.getBean("myApplicationContextAware");

        Object dog1 = myApplicationContextAware.getBeanByName("dog");
        System.out.println(dog1);
        Object person1 = myApplicationContextAware.getBeanByClass(Person.class);
        System.out.println(person1);

        System.out.println("现在开始关闭容器！");
        applicationContext.registerShutdownHook();
        System.out.println("关闭容器调用完成！");


    }

}
