package spring.bean.beanfactory;

import java.util.Set;
import spring.bean.beandefinition.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    boolean containsBeanDefinition(String beanName);

    BeanDefinition getBeanDefinition(String beanName);

    Set<BeanDefinition> getBeanDefinitions();

    Set<BeanDefinition> getBeanDefinitions(Class<?> clazz);
}
