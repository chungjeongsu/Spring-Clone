package spring.bean.def;

import spring.bean.def.scan.exception.AnnotationAccessException;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;

public class MergedAnnotations {
    private final Map<String, MergedAnnotation> mergedAnnotations;

    public MergedAnnotations(Map<String, MergedAnnotation> mergedAnnotations) {
        this.mergedAnnotations = mergedAnnotations;
    }

    public boolean hasAnnotation(String annotationName) {
        return mergedAnnotations.containsKey(annotationName);
    }

    public MergedAnnotation getAnnotation(String annotationName) {
        if(!mergedAnnotations.containsKey(annotationName))
            throw new AnnotationAccessException("annotation이 존재하지 않습니다. annotationName= " + annotationName);

        return mergedAnnotations.get(annotationName);
    }
}
