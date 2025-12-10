package spring.bean.classpathscan.exception;

public class BeanNameDuplicateException extends RuntimeException {
    public BeanNameDuplicateException(String message) {
        super(message);
    }
}
