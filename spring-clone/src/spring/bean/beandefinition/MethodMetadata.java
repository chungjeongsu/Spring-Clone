package spring.bean.beandefinition;

import spring.annotation.Bean;

public class MethodMetadata {
    private final String methodName;
    private final Class<?> declaringClassName;
    private final Class<?> returnType;
    private final MergedAnnotations mergedAnnotations;

    public MethodMetadata(String methodName, Class<?> declaringClassName, Class<?> returnType,
        MergedAnnotations mergedAnnotations) {
        this.methodName = methodName;
        this.declaringClassName = declaringClassName;
        this.returnType = returnType;
        this.mergedAnnotations = mergedAnnotations;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?> getDeclaringClassName() {
        return declaringClassName;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public boolean hasBeanAnnotation() {
        return mergedAnnotations.hasAnnotation("Bean");
    }
}
