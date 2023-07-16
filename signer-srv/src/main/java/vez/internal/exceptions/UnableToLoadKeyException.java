package vez.internal.exceptions;

public class UnableToLoadKeyException extends RuntimeException {

    public UnableToLoadKeyException(String unableToLoadPrivateKey) {
        super(unableToLoadPrivateKey);
    }
}
