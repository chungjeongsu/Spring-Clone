package spring.bean.beanfactory;

import java.util.function.Supplier;

public class SessionScope implements Scope{
    @Override
    public Object get(String name, ObjectFactory<?> createBean) {

        return null;
    }
}
