package spring.bean.beandefinition;

import spring.annotation.Scope;

public interface BeanDefinition {
    String getBeanName();

    Class<?> getBeanClass();

    void setBeanName(String beanName);

    String getScope();
}