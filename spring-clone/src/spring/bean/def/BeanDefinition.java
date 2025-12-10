package spring.bean.def;

import spring.annotation.Scope.ScopeType;

public interface BeanDefinition {
    String getBeanName();

    Class<?> getBeanClass();

    ScopeType getScopeType();

    void setBeanName(String beanName);
}