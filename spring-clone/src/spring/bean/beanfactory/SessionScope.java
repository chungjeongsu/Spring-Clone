package spring.bean.beanfactory;

import java.util.function.Supplier;

public class SessionScope implements Scope{
    @Override
    public <T> void get(String name, Supplier<T> createBean) {

    }
}
