package spring.bean.def;

import spring.bean.scope.Scope;

import java.util.Collections;
import java.util.Set;

public class AnnotationMetadata {
    private final String className;
    private final Scope scope;
    private final String superClassName;
    private final Set<String> interfaceNames;
    private final Set<String> memberClassNames;
    private final Set<MethodMetadata> declaredMethods;
    private final MergedAnnotations mergedAnnotations;

    public AnnotationMetadata(String className, Scope scope, String superClassName, Set<String> interfaceNames, Set<String> memberClassNames, Set<MethodMetadata> declaredMethods, MergedAnnotations mergedAnnotations) {
        this.className = className;
        this.scope = scope;
        this.superClassName = superClassName;
        this.interfaceNames = interfaceNames;
        this.memberClassNames = memberClassNames;
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

    public boolean hasAnnotation(String annotationName) {
        return mergedAnnotations.hasAnnotation(annotationName);
    }

    public Scope getScope() {
        return scope;
    }
}
