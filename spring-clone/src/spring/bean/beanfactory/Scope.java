package spring.bean.beanfactory;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Scope {
    <T> T get(String name, Supplier<T> createBean);
}
