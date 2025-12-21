package spring.bean.beandefinition;

/**
 * MethodBeanDefinition은 @Bean을 통해 만들어지는 Bean들의 정의이다.
 * 부모 클래스(@Configuration)의 메서드를 invoke하면서 Bean이 생성된다.
 */
public class MethodBeanDefinition implements BeanDefinition {
    private String beanName;
    private Class<?> factoryClass;
    private String factoryMethod;
    private Class<?> returnType;

    @Override
    public String getBeanName() {
        return "";
    }

    @Override
    public Class<?> getBeanClass() {
        return null;
    }

    @Override
    public void setBeanName(String beanName) {

    }

    @Override
    public String getScope() {
        return "";
    }

    public Class<?> getFactoryClass() {
        return factoryClass;
    }

    public String getFactoryMethod() {
        return factoryMethod;
    }
}
