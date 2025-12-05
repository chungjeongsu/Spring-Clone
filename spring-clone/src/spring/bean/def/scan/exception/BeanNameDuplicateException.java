package spring.bean.def.scan.exception;

public class BeanNameDuplicateException extends RuntimeException {
    public BeanNameDuplicateException(String message) {
        super(message);
    }
}
