package exceptions;

public class LoadOperationException extends Exception {
	private static final long serialVersionUID = -7835192279391548467L;

	public LoadOperationException(String message) {
        super(message);
    }
}