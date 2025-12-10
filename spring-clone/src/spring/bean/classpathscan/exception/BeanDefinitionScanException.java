package spring.bean.classpathscan.exception;

public class BeanDefinitionScanException extends RuntimeException {

    public BeanDefinitionScanException(String message, Throwable e) {
        super(message, e);
    }

    public BeanDefinitionScanException(String message) {
        super(message);
    }
}
