package spring.bean.context;

import spring.bean.beandefinition.ConfigurationBeanDefinition;
import spring.bean.beanfactory.BeanDefinitionRegistry;
import spring.bean.beanfactory.BeanFactory;
import spring.bean.beanfactory.DefaultBeanFactory;
import spring.bean.util.AnnotationConfigUtil;

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

    }

    private void registerBeanPostProcessors() {

    }

    private void finishBeanFactoryInitialization() {

    }

    private void finishRefresh() {

    }
}
