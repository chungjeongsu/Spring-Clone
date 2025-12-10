package spring.bean.def;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MergedAnnotation {
    private final Annotation annotation;

    public MergedAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public <T> T getAttributeValue(String attributeName, Class<T> valueType) {
        Class<? extends Annotation> aClass = annotation.annotationType();
        Method method = aClass.getDeclaredMethod(attributeName);
        Object value = method.invoke(annotation);
        if(!valueType.isInstance(value)) throw new IllegalArgumentException();
        return valueType.cast(value);
    }
}
