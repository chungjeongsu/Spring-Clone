package spring.bean.def;

import spring.bean.scope.Scope;

public class AnnotatedGenericBeanDefinition implements BeanDefinition {
    private String beanName;
    private Class<?> beanClass;
    private Scope scope;

    public AnnotatedGenericBeanDefinition(String beanName, Class<?> beanClass, Scope scope) {
        this.beanName = beanName;
        this.beanClass = beanClass;
        this.scope = scope;
    }

    @Override
    public String getBeanName() {
        return beanName;
    }

    @Override
    public Class<?> getBeanClass() {
        return beanClass;
    }

    @Override
    public Scope getScope() {
        return scope;
    }

    @Override
    public void setBeanName(String beanName) {
        if(this.beanName != null) {
            throw new IllegalArgumentException("");
        }
        this.beanName = beanName;
    }
}
