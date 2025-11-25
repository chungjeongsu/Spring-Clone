package spring.bean.def;

import spring.bean.scope.Scope;

public interface BeanDefinition {
    String getBeanName();

    Class<?> getBeanClass();

    Scope getScope();
}