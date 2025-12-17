package spring.bean.beandefinition;

/**
 * @Component, Infrastructure BeanDefinition에 해당
 */
public class RootBeanDefinition implements BeanDefinition{
    private String beanName;
    private Class<?> beanClass;
    private BeanDefinitionType beanDefinitionType;

    public RootBeanDefinition(String beanName, Class<?> beanClass, BeanDefinitionType type) {
        this.beanName = beanName;
        this.beanClass = beanClass;
        this.beanDefinitionType = type;
    }

    @Override
    public String getBeanName() {
        return "";
    }

    @Override
    public Class<?> getBeanClass() {
        return null;
    }

    @Override
    public void setBeanName(String beanName) {

    }

    public enum BeanDefinitionType{
        INFRA,

        COMPONENT
    }
}
