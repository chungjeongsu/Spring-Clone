package spring.bean.def;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;

public class MergedAnnotations {
    private final Map<String, MergedAnnotation> mergedAnnotations = new LinkedHashMap<>();

    public boolean hasAnnotation(String annotationName) {
        return false;
    }

    public MergedAnnotation getAnnotation(String annotationName) {
        if(!mergedAnnotations.containsKey(annotationName)) throw new IllegalArgumentException();
        return mergedAnnotations.get(annotationName);
    }
}
