package spring.bean.beandefinition;

import java.util.Collections;
import java.util.Set;
import spring.annotation.Scope.ScopeType;

public class AnnotationMetadata {
    private final String className;
    private final ScopeType scopeType;
    private final String superClassName;
    private final Set<String> interfaceNames;
    private final Set<MethodMetadata> declaredMethods;
    private final MergedAnnotations mergedAnnotations;

    public AnnotationMetadata(String className, ScopeType scopeType, String superClassName, Set<String> interfaceNames, Set<MethodMetadata> declaredMethods, MergedAnnotations mergedAnnotations) {
        this.className = className;
        this.scopeType = scopeType;
        this.superClassName = superClassName;
        this.interfaceNames = interfaceNames;
        this.declaredMethods = declaredMethods;
        this.mergedAnnotations = mergedAnnotations;
    }

    public boolean hasSuperClass() {
        return superClassName != null;
    }

    public boolean hasInterface() {
        return interfaceNames.isEmpty();
    }

    public Set<MethodMetadata> getDeclaredMethods() {
        return Collections.unmodifiableSet(declaredMethods);
    }

    public boolean hasAnnotation(Class<?> annotationClass) {
        return mergedAnnotations.hasAnnotation(annotationClass.getName());
    }

    public ScopeType getScopeType() {
        return scopeType;
    }
}
