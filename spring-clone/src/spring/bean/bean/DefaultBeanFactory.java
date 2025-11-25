package spring.bean.bean;

import spring.annotation.Autowired;
import spring.bean.def.BeanDefinition;
import spring.bean.def.ConstructorArgumentValues;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DefaultBeanFactory implements ConfigurableBeanFactory, SingletonBeanRegistry {
    private final Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(256);
    private final Map<String, Object> singletonBeans = new ConcurrentHashMap<>(256);

    //-------------------------
    //ConfigurableBeanFactory : 빈을 생성하기 위한 기본 설정
    //- GenericApplicationContext -> ConfigurableBeanFactory 이렇게 이어짐
    //-------------------------
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        if(beanName == null || beanName.isEmpty()) throw new IllegalArgumentException("beanName은 null일 수 없음");
        if(beanDefinition == null) throw new IllegalArgumentException("beanDefinition은 null일 수 없음");
        beanDefinitions.put(beanName, beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) {
        if(beanName == null || beanName.isEmpty()) throw new IllegalArgumentException("beanName은 null일 수 없음");
        beanDefinitions.remove(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        if(beanName == null || beanName.isEmpty()) throw new IllegalArgumentException("beanName은 null일 수 없음");
        return beanDefinitions.get(beanName);
    }

    @Override
    public boolean isBeanNameInUse(String beanName) {
        if(beanName == null || beanName.isEmpty()) throw new IllegalArgumentException("beanName은 null일 수 없음");
        return beanDefinitions.containsKey(beanName);
    }

    //----------------------------
    //BeanFactory : 기본적인 BeanFactory
    //----------------------------
    @Override
    public Object getBean(String name) {

    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }


    //--------------------------------
    //SingletonBeanFactory : getBean시 초기화 담당
    //--------------------------------
    /**
     * 1. 빈 객체 생성
     * 2. 빈 채우기: 필드/세터/생성자 주입
     * 3. 초기화 이전: aware 채우기
     * 4. 초기화: @PostConsturct 존재 시 채우기
     * 5. 초기화 이후 : AOP 프록시 생성, 스케쥴러 등록, ProxyWrapping
     * 6. 최종 빈 return
     */
    @Override
    public <T> T createBean(String beanName, BeanDefinition beanDefinition, Set<?> alreadyBean) throws NoSuchMethodException {
        Class<?> objectClass = beanDefinition.getBeanClass();

        Object beanInstance = createBeanInstance(objectClass, beanDefinition);

    }

    private Object createBeanInstance(Class<?> objectClass, BeanDefinition beanDefinition) throws NoSuchMethodException {
        Constructor<?>[] constructors = objectClass.getDeclaredConstructors();
        Constructor<?> autowirableConstructor = selectAutowirableConstructor(constructors);

        return autowirableConstructor.newInstance();
    }

    private Constructor<?> selectAutowirableConstructor(Constructor<?>[] constructors) {
        Constructor<?> autowirableConstructor = null;
        if(constructors.length == 1) autowirableConstructor = constructors[0];
        List<Constructor<?>> autowiredConstructors = Arrays.stream(constructors)
                .filter(c -> c.isAnnotationPresent(Autowired.class))
                .toList();
        if(autowiredConstructors.size() > 1) throw new IllegalArgumentException();
        if(autowiredConstructors.size() == 1) autowirableConstructor = autowiredConstructors.getFirst();
        Optional<Constructor<?>> noArgsConstructor = Arrays.stream(constructors)
                .filter(constructor -> constructor.getParameterCount() == 0)
                .findFirst();
        if(autowirableConstructor == null && noArgsConstructor.isPresent()) autowirableConstructor = noArgsConstructor.get();

        if(autowirableConstructor == null) throw new IllegalArgumentException();
        return autowirableConstructor;
    }
}
