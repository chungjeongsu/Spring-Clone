package spring.bean.util;

import spring.bean.bdrpp.ConfigurationClassPostProcessor;
import spring.bean.beandefinition.RootBeanDefinition;
import spring.bean.beanfactory.BeanDefinitionRegistry;
import spring.bean.bpp.AutowiredAnnotationBeanPostProcessor;
import spring.bean.bpp.CommonAnnotationBeanPostProcessor;
import spring.bean.bpp.EventListenerMethodProcessor;

/**
 * INFRA beanDefinition을 등록하는 Util 클래스
 */
public class AnnotationConfigUtil {
    private static final String CONFIG_CLASS_PP = "configurationClassPostProcessor";
    private static final String AUTOWIRED_BPP = "autowiredAnnotationBeanPostProcessor";
    private static final String COMMON_BPP = "commonAnnotationBeanPostProcessor";
    private static final String EVENT_LISTENER_PP = "eventListenerMethodProcessor";

    public static void registerAnnotationConfigProcessors(BeanDefinitionRegistry registry) {
        registerIfAbsent(registry, CONFIG_CLASS_PP, ConfigurationClassPostProcessor.class);
        registerIfAbsent(registry, AUTOWIRED_BPP, AutowiredAnnotationBeanPostProcessor.class);
        registerIfAbsent(registry, COMMON_BPP, CommonAnnotationBeanPostProcessor.class);
        registerIfAbsent(registry, EVENT_LISTENER_PP, EventListenerMethodProcessor.class);
    }

    private static void registerIfAbsent(BeanDefinitionRegistry registry, String name, Class<?> type) {
        if (registry.containsBeanDefinition(name)) return;

        registry.registerBeanDefinition(
                name,
                new RootBeanDefinition(name, type, RootBeanDefinition.BeanDefinitionType.INFRA)
        );
    }
}