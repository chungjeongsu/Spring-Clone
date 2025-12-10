package spring.bean.def.scan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import spring.bean.def.MergedAnnotation;
import spring.bean.def.MergedAnnotations;

public class AnnotationUtil {
    public static MergedAnnotations parseMergedAnnotations(Class<?> clazz) {
        Map<String, MergedAnnotation> mergedAnnotations = new LinkedHashMap<>();
        parseAnnotation(clazz.getDeclaredAnnotations(), mergedAnnotations);
        return new MergedAnnotations(mergedAnnotations);
    }

    public static MergedAnnotations parseMergedAnnotations(Method method) {
        Map<String, MergedAnnotation> mergedAnnotations = new LinkedHashMap<>();
        parseAnnotation(method.getDeclaredAnnotations(), mergedAnnotations);
        return new MergedAnnotations(mergedAnnotations);
    }

    private static void parseAnnotation(Annotation[] declaredAnnotations, Map<String, MergedAnnotation> mergedAnnotations) {
        for(Annotation annotation : declaredAnnotations) {
            Class<? extends Annotation> annotationClass = annotation.annotationType();

            if(isJavaLangAnnotation(annotationClass) || mergedAnnotations.containsKey(annotationClass.getName())) continue;

            mergedAnnotations.put(annotationClass.getName(), new MergedAnnotation(annotationClass));
            parseAnnotation(annotationClass.getDeclaredAnnotations(), mergedAnnotations);
        }
    }

    private static boolean isJavaLangAnnotation(Class<? extends Annotation> annotationClass) {
        return annotationClass.getPackageName().equals("java.lang.annotation");
    }
}
