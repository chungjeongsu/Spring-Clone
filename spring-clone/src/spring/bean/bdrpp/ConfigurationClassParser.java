package spring.bean.bdrpp;

import spring.annotation.Bean;
import spring.bean.beandefinition.BeanDefinition;
import spring.bean.beandefinition.ConfigurationBeanDefinition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * API 목록
 * 1. parseBasePackages : ConfigurationBeanDefinition들을 받아 basePackages를 추출
 * 2. parseConfigClasses : ConfigurationBeanDefinitions를 파싱해 ConfigClass(beanDefinition 설정 정보)를 생성 후 return
 */
public class ConfigurationClassParser {

    //ConfigurationBeanDefinition 들을 받아 basePackages를 추출
    public List<String> parseBasePackages(Set<BeanDefinition> configurationBeanDefinitions) {
        List<String> basePackages = new ArrayList<>();

        for(BeanDefinition beanDefinition : configurationBeanDefinitions) {
            if(beanDefinition instanceof ConfigurationBeanDefinition configurationBeanDefinition) {
                String basePackage = parseBasePackage(configurationBeanDefinition);
                basePackages.add(basePackage);
            }
        }

        return basePackages;
    }

    //BeanDefinitions 들을 받아, @Configuration 빈인 것들만 ConfigClasses return
    public Set<ConfigClass> parseConfigClasses(Set<BeanDefinition> beanDefinitions) {
        Set<ConfigClass> configClasses = new LinkedHashSet<>();

        /*
        Todo : @Import, @ComponentScan 후에 확장 기능 있을 시 기능 추가 요망
        현재는 @Bean, @Configuration만 파싱
         */
        for(BeanDefinition beanDefinition : beanDefinitions) {
            if(beanDefinition instanceof ConfigurationBeanDefinition configurationBeanDefinition) {
                Class<?> beanClass = configurationBeanDefinition.getBeanClass();

                ConfigClass configClass = new ConfigClass(
                        configurationBeanDefinition.getBeanName(),
                        parseFactoryMethods(beanClass)
                );
            }
        }
        return configClasses;
    }

    private String parseBasePackage(BeanDefinition target) {
        Class<?> beanClass = target.getBeanClass();
        return beanClass.getPackageName();
    }

    private Set<String> parseFactoryMethods(Class<?> beanClass) {
        Set<String> factoryMethods = new HashSet<>();
        Method[] declaredMethods = beanClass.getDeclaredMethods();

        for(Method method : declaredMethods) {
            Bean beanAnnotation = method.getDeclaredAnnotation(Bean.class);

            if(beanAnnotation == null) continue;
            factoryMethods.add(method.getName());
        }
        return factoryMethods;
    }
}
