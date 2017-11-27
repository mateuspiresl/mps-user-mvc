package exceptions;

public class WallExistsException extends WallException {
	private static final long serialVersionUID = 5907771420631504807L;

	public WallExistsException(String message) {
		super(message);
	}
	
	public WallExistsException() {
		super("Já existe uma parede com este nome!");
	}
}