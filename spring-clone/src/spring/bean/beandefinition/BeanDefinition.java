package spring.bean.beandefinition;

public interface BeanDefinition {
    String getBeanName();

    Class<?> getBeanClass();

    void setBeanName(String beanName);
}