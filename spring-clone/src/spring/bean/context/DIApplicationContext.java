package spring.bean.context;

import spring.bean.bdrpp.BeanDefinitionRegistryPostProcessor;
import spring.bean.beandefinition.BeanDefinition;
import spring.bean.beanfactory.DefaultBeanFactory;
import spring.bean.bfpp.BeanFactoryPostProcessor;
import spring.bean.bpp.BeanPostProcessor;
import spring.bean.util.AnnotationConfigUtil;

import java.util.Set;

public class DIApplicationContext {
    private final DefaultBeanFactory defaultBeanFactory;

    public DIApplicationContext(DefaultBeanFactory defaultBeanFactory) {
        this.defaultBeanFactory = defaultBeanFactory;
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
