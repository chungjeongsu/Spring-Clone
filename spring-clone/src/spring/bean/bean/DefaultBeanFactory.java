package spring.bean.bean;

import java.lang.reflect.InvocationTargetException;

import spring.annotation.Autowired;
import spring.bean.def.BeanDefinition;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements ConfigurableBeanFactory, SingletonBeanRegistry {
    private final Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(256);
    private final Map<String, Object> singletonBeans = new ConcurrentHashMap<>(256);
    private final Set<String> singletonsCurrentlyCreation = ConcurrentHashMap.newKeySet(16);

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
    public Object getBean(String name)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(singletonBeans.containsKey(name)) return singletonBeans.get(name);
        if(!beanDefinitions.containsKey(name)) throw new IllegalArgumentException();
        return createBean(name, beanDefinitions.get(name));
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return requiredType.cast(getBean(name));
    }

    @Override
    public <T> T getBean(Class<T> requiredType)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return getBean(requiredType.getName(), requiredType);
    }

    @Override
    public boolean containsBean(String name) {
        return this.singletonBeans.containsKey(name);
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
    public Object createBean(String beanName, BeanDefinition beanDefinition)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        beforeSingletonCreation(beanName);

        Class<?> objectClass = beanDefinition.getBeanClass();
        if(containsBean(beanName)) throw new IllegalAccessException();
        Object beanInstance = createBeanInstance(objectClass, beanDefinition);
        //Todo : 초기화 이전 ~ 초기화 이후 로직

        afterSingletonCreation(beanName);
        return beanInstance;
    }

    private Object createBeanInstance(Class<?> objectClass, BeanDefinition beanDefinition)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> autowirableConstructor = selectAutowirableConstructor(objectClass.getDeclaredConstructors());
        Object[] parameterValues = resolveParameterValues(autowirableConstructor);
        return autowirableConstructor.newInstance(parameterValues);
    }

    private Object[] resolveParameterValues(Constructor<?> autowirableConstructor)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<?>[] parameterTypes = autowirableConstructor.getParameterTypes();
        int parameterCount = autowirableConstructor.getParameterCount();
        Object[] parameterValues = new Object[parameterCount];
        for(int i = 0; i < parameterCount; i++) {
            parameterValues[i] = getBean(parameterTypes[i]);
        }
        return parameterValues;
    }

    private Constructor<?> selectAutowirableConstructor(Constructor<?>[] constructors) {
        Constructor<?> autowirableConstructor = null;

        if(constructors.length == 1) autowirableConstructor = constructors[0];  //생성자가 1개만 존재하는 경우
        parseAutowiredConstructor(constructors, autowirableConstructor);        //생성자가 1개가 아닌 경우 Autowired 붙은 생성자 파싱
        parseNoArgsConstructor(constructors, autowirableConstructor);           //생성자가 1개가 아닌 경우 기본 생성자가 있으면 파싱
        if(autowirableConstructor == null) throw new IllegalArgumentException();//생성자 1개가 아니고, 기본 생성자도 없고, Autowired도 없을 시 예외

        return autowirableConstructor;
    }

    private void parseNoArgsConstructor(Constructor<?>[] constructors,
        Constructor<?> autowirableConstructor) {
        Optional<Constructor<?>> noArgsConstructor = Arrays.stream(constructors)
                .filter(constructor -> constructor.getParameterCount() == 0)
                .findFirst();
        if(autowirableConstructor == null && noArgsConstructor.isPresent()) autowirableConstructor = noArgsConstructor.get();
    }

    private void parseAutowiredConstructor(Constructor<?>[] constructors,
        Constructor<?> autowirableConstructor) {
        List<Constructor<?>> autowiredConstructors = Arrays.stream(constructors)
                .filter(c -> c.isAnnotationPresent(Autowired.class))
                .toList();
        if(autowiredConstructors.size() > 1) throw new IllegalArgumentException();
        if(autowiredConstructors.size() == 1) autowirableConstructor = autowiredConstructors.getFirst();
    }

    private void beforeSingletonCreation(String beanName) {
        if(!this.singletonsCurrentlyCreation.contains(beanName)) {
            singletonsCurrentlyCreation.add(beanName);
            return;
        }
        throw new IllegalArgumentException();
    }

    private void afterSingletonCreation(String beanName) {
        if(this.singletonsCurrentlyCreation.contains(beanName)) {
            singletonsCurrentlyCreation.remove(beanName);
            return;
        }
        throw new IllegalArgumentException();
    }
}
