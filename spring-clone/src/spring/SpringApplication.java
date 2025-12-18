package spring;

import spring.bean.context.DIApplicationContext;

public class SpringApplication {

    public static void run(Class<?> primarySource) {
        DIApplicationContext diApplicationContext = new DIApplicationContext(primarySource);
    }
}
