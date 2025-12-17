package spring.bean.configclassread;

import spring.bean.beandefinition.ConfigurationBeanDefinition;
import spring.bean.beandefinition.BeanDefinition;
import spring.bean.classpathscan.exception.BeanNameDuplicateException;
import spring.bean.beanfactory.BeanDefinitionRegistry;

import java.util.LinkedHashSet;
import java.util.Set;
import spring.bean.configclassread.exception.ConfigurationBeanDefinitionClassException;

/**
 * ConfigurationBeanDefinition을 파라미터로 받아서, 내부 MethodBeanDefinition을 읽고 등록
 */
public class MethodBeanDefinitionReader {
    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public MethodBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void loadBeanDefinition(Set<BeanDefinition> configurationBeanDefinition) {

    }
}
