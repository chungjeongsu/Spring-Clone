package spring.bean.configclassread;

import spring.bean.beandefinition.ConfigurationBeanDefinition;
import spring.bean.beandefinition.BeanDefinition;
import spring.bean.beandefinition.MethodBeanDefinition;
import spring.bean.beandefinition.MethodMetadata;
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
        Set<BeanDefinition> beanDefinitionsByMethod = new LinkedHashSet<>();

        Set<BeanDefinition> candidates = findCandidatesByMethod(
            configurationBeanDefinition, beanDefinitionsByMethod
        );

        for(BeanDefinition beanDefinition : candidates) {
            if(beanDefinitionRegistry.containBeanDefinition(beanDefinition.getBeanName())) {
                throw new BeanNameDuplicateException("중복된 빈 이름이 존재합니다. beanName = " + beanDefinition.getBeanName());
            }
            beanDefinitionRegistry.registerBeanDefinition(beanDefinition.getBeanName(), beanDefinition);
        }
    }

    private Set<BeanDefinition> findCandidatesByMethod(Set<BeanDefinition> beanDefinitions, Set<BeanDefinition> beanDefinitionsByMethod) {
        for(BeanDefinition beanDefinition : beanDefinitions) {

            if(!(beanDefinition instanceof ConfigurationBeanDefinition configBeanDefinition))
                throw new ConfigurationBeanDefinitionClassException(
                    "해당 BeanDefinition은 ConfigurationBeanDefinition이 아닙니다. beanName= " + beanDefinition.getBeanName()
                );

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
