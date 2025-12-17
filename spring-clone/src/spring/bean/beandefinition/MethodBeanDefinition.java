package spring.bean.beandefinition;

public class MethodBeanDefinition implements BeanDefinition {
    private String beanName;
    private Class<?> beanClass;

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
}
