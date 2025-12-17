package spring.bean.bdrpp;

import spring.bean.beanfactory.BeanDefinitionRegistry;

public interface BeanDefinitionRegistryPostProcessor {
    void postProcessorBeanDefinitionRegistry(BeanDefinitionRegistry registry);
}
