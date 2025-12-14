package spring.bean.beanfactory;

import java.lang.reflect.InvocationTargetException;

public interface BeanFactory {
    Object getBean(String name)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    <T> T getBean(String name, Class<T> requiredType)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    <T> T getBean(Class<T> requiredType)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);
}
