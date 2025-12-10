package spring.bean.def.scan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import spring.bean.def.MergedAnnotation;
import spring.bean.def.MergedAnnotations;

public class AnnotationUtil {
    public static MergedAnnotations parseMergedAnnotations(Class<?> clazz) {
        Map<String, Class<? extends MergedAnnotation>> mergedAnnotations = new LinkedHashMap<>();

        return parseAnnotation(clazz.getDeclaredAnnotations(), mergedAnnotations);
    }

    public static MergedAnnotations parseMergedAnnotations(Method method) {
        Map<String, Class<? extends MergedAnnotation>> mergedAnnotations = new LinkedHashMap<>();

        return parseAnnotation(method.getDeclaredAnnotations(), mergedAnnotations);
    }

    private static void parseAnnotation(Annotation[] declaredAnnotations,
        Map<String, Class<? extends MergedAnnotation>> mergedAnnotations) {
        for(Annotation annotation : declaredAnnotations) {
            Class<? extends Annotation> annotationClass = annotation.annotationType();

            if(isJavaLangAnnotation(annotationClass)) continue;


            mergedAnnotations.put(annotationClass.getName(), parseMergedAnnotation(annotationClass));
        }
    }

    private static Class<? extends MergedAnnotation> parseMergedAnnotation(
        Class<? extends Annotation> annotationClass) {
        return null;
    }

    private static boolean isJavaLangAnnotation(Class<? extends Annotation> annotationClass) {
        return annotationClass.getPackageName().equals("java.lang.annotation");
    }
}
