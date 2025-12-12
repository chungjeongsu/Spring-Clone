package spring.bean.classpathscan;

import spring.annotation.Component;
import spring.annotation.Configuration;
import spring.bean.beandefinition.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import spring.annotation.Scope.ScopeType;

public class BeanDefinitionParser {

    public BeanDefinition parse(Class<?> clazz) {
        AnnotationMetadata annotationMetadata = parseAnnotationMetadata(clazz);
        if(hasBeanAnnotation(annotationMetadata)) return null;

        return new AnnotatedGenericBeanDefinition(
            null,
                clazz,
                annotationMetadata
        );
    }

    private AnnotationMetadata parseAnnotationMetadata(Class<?> clazz) {
        MergedAnnotations mergedAnnotations = parseMergedAnnotations(clazz);

        return new AnnotationMetadata(
                clazz.getName(),
                parseScope(mergedAnnotations),
                parseSuperClassName(clazz),
                parseInterfaceNames(clazz),
                parseDeclaredMethods(clazz),
                mergedAnnotations
        );
    }

    private ScopeType parseScope(MergedAnnotations mergedAnnotations) {
        if(!mergedAnnotations.hasAnnotation("Scope")) return ScopeType.DEFAULT;
        MergedAnnotation scopeAnnotation = mergedAnnotations.getAnnotation("Scope");
        return scopeAnnotation.getAttributeValue("value", ScopeType.class);
    }

    private String parseSuperClassName(Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        if(superclass == null) return null;
        return clazz.getSuperclass().getName();
    }

    private Set<String> parseInterfaceNames(Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        if(interfaces.length == 0) return null;
        return Arrays.stream(interfaces)
                .map(Class::getName)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Set<MethodMetadata> parseDeclaredMethods(Class<?> clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        if(declaredMethods.length == 0) return null;
        return Arrays.stream(declaredMethods)
                .map(this::parseMethodMetaData)
                .collect(Collectors.toUnmodifiableSet());
    }

    private MethodMetadata parseMethodMetaData(Method method) {
        return new MethodMetadata(
            method.getName(),
            method.getDeclaringClass(),
            method.getReturnType(),
            parseMergedAnnotations(method)
        );
    }

    private <T> MergedAnnotations parseMergedAnnotations(T target) {
        if(target instanceof Class<?>) return AnnotationUtil.parseMergedAnnotations((Class<?>) target);
        if(target instanceof Method) return AnnotationUtil.parseMergedAnnotations((Method) target);
        throw new IllegalArgumentException();
    }

    private boolean hasBeanAnnotation(AnnotationMetadata annotationMetadata) {
        return annotationMetadata.hasAnnotation(Component.class)
                || annotationMetadata.hasAnnotation(Configuration.class);
    }
}
