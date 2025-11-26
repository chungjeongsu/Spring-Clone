package spring.bean.bean;

import java.lang.reflect.InvocationTargetException;
import spring.bean.def.BeanDefinition;

import java.util.Set;

public interface SingletonBeanRegistry {
    //bean 생성 파이프라인 시작
    Object createBean(String beanName, BeanDefinition beanDefinition, Set<?> alreadyBean)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
