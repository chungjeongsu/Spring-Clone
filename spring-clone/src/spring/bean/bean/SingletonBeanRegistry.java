package spring.bean.bean;

import java.lang.reflect.InvocationTargetException;
import spring.bean.def.BeanDefinition;

public interface SingletonBeanRegistry {
    //bean 생성 파이프라인 시작
    Object createBean(String beanName, BeanDefinition beanDefinition)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
