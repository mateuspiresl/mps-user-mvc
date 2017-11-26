package exceptions;

public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 6790526783586000577L;

	public UserNotFoundException(String message) {
        super(message);
    }
	
	public UserNotFoundException() {
        this("Usuário não encontrado");
    }
}