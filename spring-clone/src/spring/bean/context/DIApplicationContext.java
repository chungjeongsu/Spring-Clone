package spring.bean.context;

import spring.bean.bdrpp.BeanDefinitionRegistryPostProcessor;
import spring.bean.beandefinition.BeanDefinition;
import spring.bean.beandefinition.ConfigurationBeanDefinition;
import spring.bean.beanfactory.DefaultBeanFactory;
import spring.bean.bfpp.BeanFactoryPostProcessor;
import spring.bean.bpp.BeanPostProcessor;
import spring.bean.util.AnnotationConfigUtil;

import java.util.Set;

/**
 * Bean Definition부터 Bean 생성까지 파이프라인을 담당하는 클래스
 * 생성 : defaultBeanFactory를 생성하고, 초기 SpringCloneApplication의 BeanDefinition만 담아둔다.
 * 1. prepareRefresh():
 * 2. registerInfrastructureProcessors(): BDRPP, BFPP, BPP를 beanDefinition으로 등록한다.
 * 3. invokeBeanFactoryPostProcessors(): 등록된 BDRPP, BFPP를 차례대로 수행.
 * 4. registerBeanPostProcessors(): BPP를 빈으로 생성 후 BeanFactory에 장착
 * 5. finishBeanFactoryInitialization(): 빈생성 파이프라인 수행
 */
public class DIApplicationContext {
    private final DefaultBeanFactory defaultBeanFactory;

    public DIApplicationContext(Class<?> primarySource) {
        this.defaultBeanFactory = new DefaultBeanFactory();
        defaultBeanFactory.registerBeanDefinition(
                primarySource.getName(),
                new ConfigurationBeanDefinition(primarySource.getName(), primarySource));
    }

    public void refresh() {
        prepareRefresh();

        registerInfrastructureProcessors();

        invokeBeanFactoryPostProcessors();

        registerBeanPostProcessors();

        finishBeanFactoryInitialization();

        finishRefresh();
    }

    private void prepareRefresh() {

    }

    private void registerInfrastructureProcessors() {
        AnnotationConfigUtil.registerAnnotationConfigProcessors(defaultBeanFactory);
    }

    private void invokeBeanFactoryPostProcessors() {
        for(BeanDefinition bd : defaultBeanFactory.getBeanDefinitions(BeanDefinitionRegistryPostProcessor.class)) {
            BeanDefinitionRegistryPostProcessor bdrpp  = defaultBeanFactory.getBean(bd.getBeanName());
            bdrpp.postProcessorBeanDefinitionRegistry(defaultBeanFactory);
        }

        for(BeanDefinition bd : defaultBeanFactory.getBeanDefinitions(BeanFactoryPostProcessor.class)) {
            BeanFactoryPostProcessor bfpp = defaultBeanFactory.getBean(bd.getBeanName());
            bfpp.postProcessBeanFactory(defaultBeanFactory);
        }
    }

    private void registerBeanPostProcessors() {
        for(BeanDefinition bd : defaultBeanFactory.getBeanDefinitions(BeanPostProcessor.class)) {
            BeanPostProcessor bpp = defaultBeanFactory.getBean(bd.getBeanName());
            defaultBeanFactory.addBeanPostProcessor(bpp);
        }
    }

    private void finishBeanFactoryInitialization() {
        Set<BeanDefinition> beanDefinitions = defaultBeanFactory.getBeanDefinitions();
        for(BeanDefinition beanDefinition : beanDefinitions) {
            if(defaultBeanFactory.containsBeanDefinition(beanDefinition.getBeanName())) continue;
            defaultBeanFactory.createBean(beanDefinition.getBeanName(), beanDefinition);
        }
    }

    private void finishRefresh() {

    }
}
