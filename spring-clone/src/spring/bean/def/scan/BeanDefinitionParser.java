package spring.bean.def.scan;

import spring.bean.def.AnnotatedGenericBeanDefinition;
import spring.bean.def.BeanDefinition;
import spring.bean.scope.Scope;

public class BeanDefinitionParser {
    public BeanDefinition parse(Class<?> clazz) {
        return new AnnotatedGenericBeanDefinition(
            null,
            clazz,
            parseBeanScope(clazz)
        );
    }

    private Scope parseBeanScope(Class<?> clazz) {
        clazz.getDeclaredAnnotation();
    }
}
