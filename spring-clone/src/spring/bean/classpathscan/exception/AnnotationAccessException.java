package spring.bean.classpathscan.exception;

public class AnnotationAccessException extends RuntimeException {
    public AnnotationAccessException(String message, Throwable e) {
      super(message, e);
    }

    public AnnotationAccessException(String message) {
        super(message);
    }
}
