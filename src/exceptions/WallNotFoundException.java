package exceptions;

public class WallNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 582777763232008995L;

	public WallNotFoundException(String message) {
		super(message);
	}
	
	public WallNotFoundException() {
		super();
	}
}
