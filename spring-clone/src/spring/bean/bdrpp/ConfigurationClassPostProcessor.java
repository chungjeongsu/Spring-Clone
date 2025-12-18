package spring.bean.bdrpp;

import spring.bean.beandefinition.BeanDefinition;
import spring.bean.beandefinition.ConfigurationBeanDefinition;
import spring.bean.beanfactory.BeanDefinitionRegistry;
import spring.bean.classpathscan.ClassPathBeanDefinitionScanner;

import java.util.List;
import java.util.Set;

/**
 * @Component, @Configuration 빈 정의를 등록하기 위한 오케스트레이션 객체
 */
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor {

    /**
     * 1. 이미 등록된 SpringCloneApplication bean definition을 가져옴
     * 2. parser를 생성 후 bean definition을 통해 basePackages 해석(가져옴)
     * 3. basePackage 기반 beanDefinition 등록
     * 4. parser를 통해, 등록된 beanDefinition들 분석, configClass 형식으로 return
     * 5. configClass를 통해 beanDefinition 업데이트
     * => @Component, @Configuration이 사용된 클래스들의 완성본 beanDefinition들이 registry에 등록됨
     * => 현재는 5번의 과정에서 @Configuration bean definition만 enhance하고 있음
     */
    @Override
    public void postProcessorBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        Set<BeanDefinition> configCandidates = registry.getBeanDefinitions(ConfigurationBeanDefinition.class);

        ConfigurationClassParser parser = new ConfigurationClassParser();
        List<String> basePackages = parser.parseBasePackages(configCandidates);

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.scan(basePackages);

        Set<ConfigClass> parsedConfigClasses = parser.parseConfigClasses(registry.getBeanDefinitions());
        enhanceConfigurationBeanDefinitions(parsedConfigClasses, registry);
    }

    private void enhanceConfigurationBeanDefinitions(Set<ConfigClass> parsedConfigClasses, BeanDefinitionRegistry registry) {
        for(ConfigClass configClass : parsedConfigClasses) {
            String beanName = configClass.getBeanName();
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);

            if(beanDefinition instanceof ConfigurationBeanDefinition configurationBeanDefinition) {
                configurationBeanDefinition.enhance(configClass);
            }
        }
    }
}
