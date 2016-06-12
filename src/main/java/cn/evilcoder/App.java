package cn.evilcoder;

import cn.evilcoder.service.TestService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String[] locations = {"beans.xml"};
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext(locations);
        TestService testService = (TestService) ctx.getBean("testService");
        System.out.println(testService.test(System.currentTimeMillis() + ""));
        ctx.destroy();// 关闭 Spring 容器，以触发 Bean 销毁方法的执行
    }
}
