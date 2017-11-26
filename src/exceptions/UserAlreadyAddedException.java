package exceptions;

public class UserAlreadyAddedException extends Exception {
	private static final long serialVersionUID = -4602925985901144587L;

	public UserAlreadyAddedException(String message) {
        super(message);
    }
	
	public UserAlreadyAddedException() {
        this("Usuário existente");
    }
}