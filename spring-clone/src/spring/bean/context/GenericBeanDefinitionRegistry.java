package spring.bean.context;

import spring.bean.bean.DefaultBeanFactory;
import spring.bean.beandefinition.BeanDefinition;

public class GenericBeanDefinitionRegistry implements BeanDefinitionRegistry {
    private final DefaultBeanFactory defaultBeanFactory;

    public GenericBeanDefinitionRegistry(DefaultBeanFactory defaultBeanFactory) {
        this.defaultBeanFactory = defaultBeanFactory;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        if(beanName == null || beanName.isEmpty()) throw new IllegalArgumentException("beanName은 null일 수 없음");
        if(beanDefinition == null) throw new IllegalArgumentException("beanDefinition은 null일 수 없음");
        this.defaultBeanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public boolean containBeanDefinition(String beanName) {
        return this.defaultBeanFactory.containsBean(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.defaultBeanFactory.getBeanDefinition(beanName);
    }
}
