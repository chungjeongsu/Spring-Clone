package spring.bean.beandefinition;

import spring.annotation.Bean;
import spring.annotation.Scope;
import spring.bean.bdrpp.ConfigClass;

import java.util.Set;

/**
 * @Configuration 기반 빈 정의이다.
 * 생성자를 통해 빈이 생성된다.
 */
public class ConfigurationBeanDefinition implements BeanDefinition {
    private String beanName;
    private Class<?> beanClass;
    private Set<String> factoryMethods;

    public ConfigurationBeanDefinition(String beanName, Class<?> beanClass) {
        this.beanName = beanName;
        this.beanClass = beanClass;
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
    public void setBeanName(String beanName) {
        if(this.beanName != null) {
            throw new IllegalArgumentException("bean 이름이 이미 있습니다. beanName = " + beanName);
        }
        this.beanName = beanName;
    }

    public void enhance(ConfigClass configClass) {
        if(!configClass.hasFactoryMethods()) return;
        this.factoryMethods = configClass.getFactoryMethods();
    }
}
