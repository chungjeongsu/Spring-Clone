package spring.bean.def;

import spring.annotation.Scope.ScopeType;

public class AnnotatedGenericBeanDefinition implements BeanDefinition {
    private String beanName;
    private Class<?> beanClass;
    private AnnotationMetadata annotationMetadata;

    public AnnotatedGenericBeanDefinition(String beanName, Class<?> beanClass, AnnotationMetadata annotationMetadata) {
        this.beanName = beanName;
        this.beanClass = beanClass;
        this.annotationMetadata = annotationMetadata;
    }

    @Override
    public String getBeanName() {
        return beanName;
    }

    @Override
    public Class<?> getBeanClass() {
        return beanClass;
    }

    @Override
    public ScopeType getScopeType() {
        return annotationMetadata.getScopeType();
    }

    @Override
    public void setBeanName(String beanName) {
        if(this.beanName != null) {
            throw new IllegalArgumentException("");
        }
        this.beanName = beanName;
    }
}
