package spring.bean.def;

import spring.bean.scope.Scope;

public interface BeanDefinition {
    String getBeanName();

    Class<?> getBeanClass();

    Scope getScope();

    String[] getDependsOn();

    void setDependsOn(String[] dependsOn);
}