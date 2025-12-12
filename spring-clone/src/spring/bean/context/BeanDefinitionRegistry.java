package spring.bean.context;

import spring.bean.beandefinition.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    boolean containBeanDefinition(String beanName);

    BeanDefinition getBeanDefinition(String beanName);
}
