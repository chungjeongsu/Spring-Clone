package spring.bean.def;

import spring.bean.def.scan.exception.AnnotationAccessException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MergedAnnotation {
    private final Class<? extends Annotation> annotationClass;

    public MergedAnnotation(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public <T> T getAttributeValue(String attributeName, Class<T> valueType) {
        try {
            Method method = annotationClass.getDeclaredMethod(attributeName);
            Object value = method.invoke(annotationClass);

            if(!valueType.isInstance(value)) throw new IllegalArgumentException();

            return valueType.cast(value);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new AnnotationAccessException(
                    "Annotation Attribute 접근 예외 발생했습니다. Annotation = "
                            + annotationClass.getName()
                    + "attribute = "
                            + attributeName
            );
        }
    }
}
