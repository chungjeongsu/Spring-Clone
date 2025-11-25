package spring.bean.def;

import spring.bean.scope.Scope;

public class AnnotatedGenericBeanDefinition implements AnnotatedBeanDefinition{
    private String beanName;
    private Class<?> beanClass;
    private Scope scope;
    private AnnotationMetadata annotationMetadata;

    private String[] dependsOn;

    private AnnotatedGenericBeanDefinition(String beanName, Class<?> beanClass, Scope scope, AnnotationMetadata annotationMetadata, ConstructorArgumentValues constructorArgumentValues) {
        this.beanName = requireBeanName(beanName);
        this.beanClass = requireBeanClass(beanClass);
        this.scope = requireScope(scope);
        this.annotationMetadata = requireAnnotationMetadata(annotationMetadata);
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
    public Scope getScope() {
        return scope;
    }

    @Override
    public AnnotationMetadata getMetaData() {
        return annotationMetadata;
    }

    private String requireBeanName(String beanName) {
        if(beanName.isEmpty()) throw new IllegalArgumentException("빈 이름은 null일 수 없음");
        return beanName;
    }

    private Class<?> requireBeanClass(Class<?> beanClass) {
        if(beanClass == null) throw new IllegalArgumentException("beanClass는 null일 수 없음");
        return beanClass;
    }

    private Scope requireScope(Scope scope) {
        if(scope == null) throw new IllegalArgumentException("scope는 null일 수 없음");
        return scope;
    }

    private AnnotationMetadata requireAnnotationMetadata(AnnotationMetadata annotationMetadata) {
        if(annotationMetadata == null) throw new IllegalArgumentException("annotationMetadata는 null일 수 없음");
        return annotationMetadata;
    }

    public static AnnotatedGenericBeanDefinition of(String beanName, Class<?> beanClass, Scope scope, AnnotationMetadata annotationMetadata, ConstructorArgumentValues constructorArgumentValues) {
        return new AnnotatedGenericBeanDefinition(beanName, beanClass, scope, annotationMetadata, constructorArgumentValues);
    }
}
