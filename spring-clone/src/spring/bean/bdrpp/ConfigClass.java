package spring.bean.bdrpp;

import java.util.Set;

public class ConfigClass {
    private final String beanDefinitionName;
    private final Set<String> factoryMethods;

    public ConfigClass(String beanDefinitionName, Set<String> factoryMethods) {
        this.beanDefinitionName = beanDefinitionName;
        this.factoryMethods = factoryMethods;
    }

    public String getBeanName() {
        return beanDefinitionName;
    }

    public boolean hasFactoryMethods() {
        return !factoryMethods.isEmpty();
    }

    public Set<String> getFactoryMethods() {
        return factoryMethods;
    }
}
