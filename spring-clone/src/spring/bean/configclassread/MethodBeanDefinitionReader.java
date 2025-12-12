package spring.bean.configclassread;

import spring.bean.beandefinition.AnnotatedGenericBeanDefinition;
import spring.bean.beandefinition.BeanDefinition;
import spring.bean.beandefinition.MethodMetadata;
import spring.bean.classpathscan.AnnotationBeanNameGenerator;
import spring.bean.classpathscan.BeanDefinitionParser;
import spring.bean.context.BeanDefinitionRegistry;

import java.util.LinkedHashSet;
import java.util.Set;

public class MethodBeanDefinitionReader {
    private final BeanDefinitionRegistry beanDefinitionRegistry;
    private final AnnotationBeanNameGenerator beanNameGenerator;
    private final BeanDefinitionParser beanDefinitionParser;

    public MethodBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry, AnnotationBeanNameGenerator beanNameGenerator, BeanDefinitionParser beanDefinitionParser) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        this.beanNameGenerator = beanNameGenerator;
        this.beanDefinitionParser = beanDefinitionParser;
    }

    public void loadBeanDefinition(Set<BeanDefinition> beanDefinitions) {
        Set<BeanDefinition> beanDefinitionsByMethod = new LinkedHashSet<>();
        Set<BeanDefinition> candidates = findCandidatesByMethod(beanDefinitions, beanDefinitionsByMethod);
        for(BeanDefinition beanDefinition : candidates) {
            String beanName = beanNameGenerator.generateBeanName(beanDefinition, beanDefinitionRegistry);
            beanDefinition.setBeanName(beanName);

            beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
        }
    }

    private Set<BeanDefinition> findCandidatesByMethod(Set<BeanDefinition> beanDefinitions, Set<BeanDefinition> beanDefinitionsByMethod) {
        for(BeanDefinition beanDefinition : beanDefinitions) {
            AnnotatedGenericBeanDefinition configBeanDefinition = (AnnotatedGenericBeanDefinition) beanDefinition;
            readBeanDefinition(configBeanDefinition.getBeanMethodMetadata(), beanDefinitionsByMethod);
        }
        return beanDefinitionsByMethod;
    }

    private void readBeanDefinition(Set<MethodMetadata> beanMethodMetadata, Set<BeanDefinition> beanDefinitionsByMethod) {
        for(MethodMetadata methodMetadata : beanMethodMetadata) {
            Class<?> returnType = methodMetadata.getReturnType();
            BeanDefinition parsedbeanDefinition = beanDefinitionParser.parse(returnType);
            if(parsedbeanDefinition != null) beanDefinitionsByMethod.add(parsedbeanDefinition);
        }
    }
}
