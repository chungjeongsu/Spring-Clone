package spring.bean.beanfactory;

import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import spring.annotation.Autowired;
import spring.bean.beandefinition.BeanDefinition;
import spring.bean.beandefinition.MethodBeanDefinition;
import spring.bean.bpp.BeanPostProcessor;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory, SingletonBeanRegistry,
    BeanDefinitionRegistry {
    private final ScopeRegistry scopeRegistry;

    private final Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(256);
    private final Map<String, Object> singletonBeans = new ConcurrentHashMap<>(256);
    private final Map<String, ObjectFactory<?>> singletonFactories = new LinkedHashMap<>();
    private final Set<String> singletonsCurrentlyCreation = ConcurrentHashMap.newKeySet(16);

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public DefaultBeanFactory() {
        this.scopeRegistry = new ScopeRegistry();
    }

    //-------------------------
    //BeanDefinitionRegistry 구현
    //-------------------------
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        if(beanName == null || beanName.isEmpty()) throw new IllegalArgumentException("beanName은 null일 수 없음");
        if(beanDefinition == null) throw new IllegalArgumentException("beanDefinition은 null일 수 없음");
        beanDefinitions.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitions.containsKey(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        if(beanName == null || beanName.isEmpty()) throw new IllegalArgumentException("beanName은 null일 수 없음");
        return beanDefinitions.get(beanName);
    }

    @Override
    public Set<BeanDefinition> getBeanDefinitions() {
        return getBeanDefinitions(Object.class);
    }

    @Override
    public Set<BeanDefinition> getBeanDefinitions(Class<?> clazz) {
        return beanDefinitions.values().stream()
            .filter(clazz::isInstance)
            .collect(Collectors.toSet());
    }

    //----------------------------
    //BeanFactory : 기본적인 BeanFactory 구현
    //----------------------------
    @Override
    public Object getBean(String name) {
        if(singletonBeans.containsKey(name)) return singletonBeans.get(name);
        if(!beanDefinitions.containsKey(name)) throw new IllegalArgumentException();

        BeanDefinition beanDefinition = beanDefinitions.get(name);

        if(beanDefinition.getScope().equals("default") ||beanDefinition.getScope().equals("singleton")) {
            return createBean(name, beanDefinition);
        }

        Scope scope = scopeRegistry.get(beanDefinition.getScope());
        return scope.get(name, () -> createBean(name, beanDefinition));
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return requiredType.cast(getBean(name));
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return getBean(requiredType.getName(), requiredType);
    }

    //--------------------------------
    //SingletonBeanFactory : getBean시 초기화 담당
    //--------------------------------
    /**
     */
    @Override
    public Object createBean(String beanName, BeanDefinition beanDefinition) {

        beforeSingletonCreation(beanName);

        Object beanInstance = createBeanInstance(beanDefinition);

        populateBean(beanName, beanDefinition, beanInstance);

        initializeBean(beanName, beanDefinition, beanInstance);

        afterSingletonCreation(beanName);

        return beanInstance;
    }

    private void beforeSingletonCreation(String beanName) {
        if(!this.singletonsCurrentlyCreation.contains(beanName)) {
            singletonsCurrentlyCreation.add(beanName);
            return;
        }
        throw new IllegalArgumentException();
    }

    private Object createBeanInstance(BeanDefinition beanDefinition) {
        if(beanDefinition instanceof MethodBeanDefinition methodBeanDefinition) {
            return createMethodBeanInstance(methodBeanDefinition);
        }
        return createClassBeanInstance(beanDefinition);
    }

    private void populateBean(String beanName, BeanDefinition beanDefinition, Object beanInstance) {

    }

    private void initializeBean(String beanName, BeanDefinition beanDefinition, Object beanInstance) {

    }

    private void afterSingletonCreation(String beanName) {
        if(this.singletonsCurrentlyCreation.contains(beanName)) {
            singletonsCurrentlyCreation.remove(beanName);
            return;
        }
        throw new IllegalArgumentException();
    }

    private Object createMethodBeanInstance(MethodBeanDefinition methodBeanDefinition){
        Class<?> factoryClass = methodBeanDefinition.getFactoryClass();
        try{
            Object factoryBean = getBean(factoryClass);
            Method factoryMethod = factoryClass.getDeclaredMethod(methodBeanDefinition.getFactoryMethod());
            return factoryMethod.invoke(factoryBean);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private Object createClassBeanInstance(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?> autowirableConstructor = selectAutowirableConstructor(beanClass.getDeclaredConstructors());
        Object[] parameterValues = null;
        try {
            parameterValues = resolveParameterValues(autowirableConstructor);
            return autowirableConstructor.newInstance(parameterValues);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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
        if(autowiredConstructors.size() == 1) autowirableConstructor = autowiredConstructors.get(0);
    }
}
