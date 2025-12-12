package spring.bean.configclassread;

import spring.bean.beandefinition.ConfigurationBeanDefinition;
import spring.bean.beandefinition.BeanDefinition;
import spring.bean.beandefinition.MethodBeanDefinition;
import spring.bean.beandefinition.MethodMetadata;
import spring.bean.classpathscan.exception.BeanNameDuplicateException;
import spring.bean.context.BeanDefinitionRegistry;

import java.util.LinkedHashSet;
import java.util.Set;

public class MethodBeanDefinitionReader {
    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public MethodBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void loadBeanDefinition(Set<BeanDefinition> beanDefinitions) {
        Set<BeanDefinition> beanDefinitionsByMethod = new LinkedHashSet<>();
        Set<BeanDefinition> candidates = findCandidatesByMethod(beanDefinitions, beanDefinitionsByMethod);
        for(BeanDefinition beanDefinition : candidates) {
            if(beanDefinitionRegistry.containBeanDefinition(beanDefinition.getBeanName())) {
                throw new BeanNameDuplicateException("중복된 빈 이름이 존재합니다. beanName = " + beanDefinition.getBeanName());
            }
            beanDefinitionRegistry.registerBeanDefinition(beanDefinition.getBeanName(), beanDefinition);
        }
    }

    private Set<BeanDefinition> findCandidatesByMethod(Set<BeanDefinition> beanDefinitions, Set<BeanDefinition> beanDefinitionsByMethod) {
        for(BeanDefinition beanDefinition : beanDefinitions) {
            ConfigurationBeanDefinition configBeanDefinition = (ConfigurationBeanDefinition) beanDefinition;
            readBeanDefinition(beanDefinition, configBeanDefinition.getBeanMethodMetadata(), beanDefinitionsByMethod);
        }
        return beanDefinitionsByMethod;
    }

    private void readBeanDefinition(BeanDefinition beanDefinition, Set<MethodMetadata> beanMethodMetadata, Set<BeanDefinition> beanDefinitionsByMethod) {
        for(MethodMetadata methodMetadata : beanMethodMetadata) {
            MethodBeanDefinition methodBeanDefinition = new MethodBeanDefinition(
                    methodMetadata.getMethodName(),
                    beanDefinition.getBeanName(),
                    methodMetadata.getMethodName(),
                    methodMetadata.getReturnType(),
                    beanDefinition.getScopeType()
            );
            beanDefinitionsByMethod.add(methodBeanDefinition);
        }
    }
}
