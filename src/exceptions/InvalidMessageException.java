package exceptions;

public class InvalidMessageException extends Exception {
	private static final long serialVersionUID = 2757090097579745077L;

	public InvalidMessageException(String message) {
		super(message);
	}
}