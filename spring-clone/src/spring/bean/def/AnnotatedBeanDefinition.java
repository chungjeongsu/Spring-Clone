package spring.bean.def;

import spring.bean.scope.Scope;

public interface AnnotatedBeanDefinition extends BeanDefinition{
    AnnotationMetadata getMetaData();
}
