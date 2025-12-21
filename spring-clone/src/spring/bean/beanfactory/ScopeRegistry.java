package spring.bean.beanfactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScopeRegistry {
    private final static String SINGLETON = "singleton";
    private final static String PROTOTYPE = "prototype";
    private final static String REQUEST = "request";
    private final static String SESSION = "session";

    private final Map<String, Scope> scopes = new LinkedHashMap<>();

    public ScopeRegistry() {
        scopes.put(SINGLETON, new SingletonScope());
        scopes.put(PROTOTYPE, new PrototypeScope());
        scopes.put(REQUEST, new RequestScope());
        scopes.put(SESSION, new SessionScope());
    }

    public Scope get(String scopeName) {
        if(!isScope(scopeName)) throw new ScopeException("해당 스코프가 없습니다. scopeName= " + scopeName);
        return scopes.get(scopeName);
    }

    private boolean isScope(String name) {
        return name.equals(SINGLETON) ||
                name.equals(PROTOTYPE)||
                name.equals(REQUEST)  ||
                name.equals(SESSION);
    }
}
