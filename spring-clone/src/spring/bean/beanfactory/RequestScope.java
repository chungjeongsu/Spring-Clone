package spring.bean.beanfactory;

import java.util.function.Supplier;

public class RequestScope implements Scope{
    @Override
    public <T> void get(String name, Supplier<T> createBean) {

    }
}
