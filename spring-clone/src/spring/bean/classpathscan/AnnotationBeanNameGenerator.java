package spring.bean.classpathscan;

import spring.bean.beandefinition.BeanDefinition;
import spring.bean.beanfactory.BeanDefinitionRegistry;
import spring.bean.classpathscan.exception.BeanNameDuplicateException;

public class AnnotationBeanNameGenerator {
    public String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry beanDefinitionRegistry) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        String beanName = beanClass.getName();

        if(beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            throw new BeanNameDuplicateException("중복된 빈 이름이 존재합니다. beanName = " + beanName);
        }

        return toCamelCase(beanName);
    }

    private String toCamelCase(String beanName) {
        char firstChar = beanName.charAt(0);
        return Character.toLowerCase(firstChar) + beanName.substring(1);
    }
}
