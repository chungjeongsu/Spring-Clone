package spring.bean.def.scan;

import spring.annotation.Component;
import spring.bean.def.*;
import spring.bean.scope.Scope;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanDefinitionParser {
    public BeanDefinition parse(Class<?> clazz) {
        return new AnnotatedGenericBeanDefinition(
            null,
                clazz,
                parseAnnotationMetadata(clazz)
        );
    }

    private AnnotationMetadata parseAnnotationMetadata(Class<?> clazz) {
        return new AnnotationMetadata(
                clazz.getName(),
                parseScope(clazz),
                parseSuperClassName(clazz),
                parseInterfaceNames(clazz),
                parseMemberClassNames(clazz),
                parseDeclaredMethods(clazz),
                parseMergedAnnotations(clazz)
        );
    }

    private Scope parseScope(Class<?> clazz) {
        Annotation[] declaredAnnotations = clazz.getDeclaredAnnotations();
        if(declaredAnnotations.length == 0) return Scope.DEFAULT;
        for(Annotation annotation : declaredAnnotations) {

        }
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

    private Set<String> parseMemberClassNames(Class<?> clazz) {

    }

    private Set<MethodMetadata> parseDeclaredMethods(Class<?> clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        if(declaredMethods.length == 0) return null;
        return Arrays.stream(declaredMethods)
                .map(this::parseMethodMetaData)
                .collect(Collectors.toUnmodifiableSet());
    }

    private MethodMetadata parseMethodMetaData(Method c) {

    }

    private MergedAnnotations parseMergedAnnotations(Class<?> clazz) {
        return AnnotationUtil.parseMergedAnnotations(clazz.getDeclaredAnnotations());
    }
}
