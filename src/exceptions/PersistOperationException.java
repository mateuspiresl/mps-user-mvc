package exceptions;

public class PersistOperationException extends RuntimeException {
	private static final long serialVersionUID = -625844877379088221L;

	public PersistOperationException(String message) {
        super(message);
    }
}