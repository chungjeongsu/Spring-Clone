package spring.bean.bfpp;

import spring.bean.beanfactory.BeanFactory;
import spring.bean.beanfactory.DefaultBeanFactory;

public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(BeanFactory beanFactory);
}
