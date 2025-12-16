package spring.bean.beandefinition;

import spring.annotation.Bean;
import spring.annotation.Scope;

import java.util.Set;

public class ConfigurationBeanDefinition implements BeanDefinition {
    private String beanName;
    private Class<?> beanClass;

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
}
