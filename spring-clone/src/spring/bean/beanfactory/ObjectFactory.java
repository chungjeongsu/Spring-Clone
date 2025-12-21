package spring.bean.beanfactory;

@FunctionalInterface
public interface ObjectFactory<T> {
    T getObject();
}
