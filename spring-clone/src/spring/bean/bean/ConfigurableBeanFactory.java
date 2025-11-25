package spring.bean.bean;

import spring.bean.def.BeanDefinition;

public interface ConfigurableBeanFactory extends BeanFactory {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    void removeBeanDefinition(String beanName);

    BeanDefinition getBeanDefinition(String beanName);

    boolean isBeanNameInUse(String beanName);
}
