package spring.bean.def;

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


}
