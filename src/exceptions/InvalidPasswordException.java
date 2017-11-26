package exceptions;

public class InvalidPasswordException extends Exception {
	private static final long serialVersionUID = -8627830860280404690L;

	public InvalidPasswordException(String message) {
        super(message);
    }
}