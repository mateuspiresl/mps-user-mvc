package exceptions;

public class InvalidLoginException extends Exception {
	private static final long serialVersionUID = 701964896405851214L;

	public InvalidLoginException(String message) {
        super(message);
    }
}