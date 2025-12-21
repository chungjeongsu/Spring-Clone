package spring.bean.beanfactory;

import java.lang.reflect.InvocationTargetException;
import spring.bean.beandefinition.BeanDefinition;

public interface DefaultBeanRegistry {
    //bean 생성 파이프라인 시작
    Object createBean(String beanName, BeanDefinition beanDefinition)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
