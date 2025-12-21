package spring.bean.beanfactory;

import java.util.function.Supplier;

public interface Scope {
    Object get(String name, ObjectFactory<?> createBean);
}
