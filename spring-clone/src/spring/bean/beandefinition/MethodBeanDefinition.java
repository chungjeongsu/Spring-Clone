package spring.bean.beandefinition;


import spring.annotation.Scope;

/**
 * 빈 생성 시 getBean(factoryBeanName) 이후, invoke()
 * getBean(factoryBeanName) => GGLIB 객체가 될 것임.
 */
public class MethodBeanDefinition implements BeanDefinition{
    private String beanName;
    private String factoryBeanName;
    private String factoryMethodName;
    private Class<?> returnType;
    private Scope.ScopeType scopeType;

    public MethodBeanDefinition(String beanName, String factoryBeanName, String factoryMethodName, Class<?> returnType, Scope.ScopeType scopeType) {
        this.beanName = beanName;
        this.factoryBeanName = factoryBeanName;
        this.factoryMethodName = factoryMethodName;
        this.returnType = returnType;
        this.scopeType = scopeType;
    }

    @Override
    public String getBeanName() {
        return this.beanName;
    }

    @Override
    public Class<?> getBeanClass() {
        return this.returnType;
    }

    @Override
    public Scope.ScopeType getScopeType() {
        return this.scopeType;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public String getFactoryMethodName() {
        return factoryMethodName;
    }
}
