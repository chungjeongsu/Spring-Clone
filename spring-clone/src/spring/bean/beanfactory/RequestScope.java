package spring.bean.beanfactory;

public class RequestScope implements Scope {
    @Override
    public Object get(String name, ObjectFactory<?> createBean) {
        //RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        //Object scopedObject = attributes.getAttribute(name, getScope());
        //if(scopedObject == null) {
        // scopedObject = objectFactory.getObject();
        // attributes.setAttribute(name, scopedObject, getScope());
        // Object retrievedObject = attributes.getAttribute(name,getScope());
        // if(retrievedObject != null) {
        //  scopedObject = retrievedObject;
        // }
        //}

        return createBean.getObject();
    }
}
