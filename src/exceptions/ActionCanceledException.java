package exceptions;

public class ActionCanceledException extends RuntimeException {
	private static final long serialVersionUID = -847221493188096655L;

	public ActionCanceledException(String message) {
		super(message);
	}
	
	public ActionCanceledException() {
		super("Ação cancelada.");
	}
}