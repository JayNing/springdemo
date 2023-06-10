package com.ning.springbean.destroy;

import lombok.Data;
import org.springframework.beans.factory.DisposableBean;

/**
 * desc: DisposableBean
 * createBy: Ningjianjian
 *
 * 功能特性
 * 1、DisposableBean是一个接口，为Spring bean提供了一种释放资源的方式 ，只有一个扩展方法destroy()；
 *
 * 2、实现DisposableBean接口，并重写destroy()，可以在Spring容器销毁bean的时候获得一次回调；
 *
 * 3、destroy()的回调执行时机是Spring容器关闭，需要销毁所有的bean时；
 * ————————————————
 *
 * 总结
 * 仔细琢磨一翻会发现，DisposableBean这个扩展点很简单，似乎没什么用，只有一个扩展方法destroy()，其触发时机也是在Spring容器关闭、销毁bean的时候 ，
 * 但很关键。你想呀，我们使用Springboot作为项目的开发框架，业务实际上是跑在Spring容器里的，如果Spring容器关闭的时候，业务还正在执行，
 * 这不是要出大乱子吗？所以你说这个接口有用没？肯定有用呀，优雅安全的做法就是，在Spring容器关闭，通过这个扩展接口，提前安排好相关的业务资源释放，防止出现一些不可控的业务错误。
 * ————————————————
 * 版权声明：本文为CSDN博主「凡夫贩夫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/fox9916/article/details/129026156
 */

@Data
public class FishDisposableBean implements DisposableBean {

    private String name;

    private String type;

    @Override
    public void destroy() throws Exception {
        System.out.println("Fish bean被销毁了。。。。");
    }
}
