package spring.bean.def;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    boolean containBeanDefinition(String beanName);

    BeanDefinition getBeanDefinition(String beanName);
}
