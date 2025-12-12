package spring.bean.beandefinition;

import spring.annotation.Scope.ScopeType;

public interface BeanDefinition {
    String getBeanName();

    Class<?> getBeanClass();

    ScopeType getScopeType();

    void setBeanName(String beanName);
}