package auth.core.exception;

public class MessageException extends RuntimeException {

    public MessageException(Throwable throwable) {
        super(throwable);
    }

    public MessageException(String message) {
        super(message);
    }

}
