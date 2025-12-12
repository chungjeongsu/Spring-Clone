package spring.bean.beandefinition;

import spring.annotation.Bean;
import spring.annotation.Scope.ScopeType;

import java.util.Set;

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
            throw new IllegalArgumentException("bean 이름이 이미 있습니다. beanName = " + beanName);
        }
        this.beanName = beanName;
    }

    public boolean hasBeanAnnotation() {
        return annotationMetadata.hasAnnotation(Bean.class);
    }

    public Set<MethodMetadata> getBeanMethodMetadata() {
        return annotationMetadata.getBeanMethodMetadata();
    }
}
