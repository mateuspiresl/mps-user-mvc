package business.control;

import business.model.User;
import infra.UserRepository;

public class UserManager {
        
    public static final int MAX_LOGIN_LENGTH = 12;
    public static final int MAX_PASSWORD_LENGTH = 20;
    public static final int MIN_PASSWORD_LENGTH = 8;
            
    private UserRepository repository;
    
    public UserManager(UserRepository repository) {
        this.repository = repository;
    }
    
    public void addUser(final User user) throws InvalidLoginException, InvalidPasswordException, UserRepository.UserAlreadyAddedException {
        
        final String login = user.getLogin();
        final String password = user.getPassword();
               
        if (validateLogin(login) && validatePassword(password)) {
            try {
                repository.addUser(user);                
            } catch(UserRepository.UserAlreadyAddedException e) {
                throw e;
            }         
        }
        
    }
        
    public void deleteUser(final String login) throws UserRepository.UserNotFoundException {
        try {
            repository.deleteUser(login);
        } catch(UserRepository.UserNotFoundException e) {
            throw e;
        }               
    }
    
    public void save() throws UserRepository.PersistOperationException {
        try {
            repository.persist();
        } catch(UserRepository.PersistOperationException e) {
            throw e;
        }
        
    }
    
    private boolean validateLogin(String login) throws InvalidLoginException {
        if (login.matches("\\d")) {            
            throw new InvalidLoginException("O login n�o pode conter num�ricos");
        } 
        else if (login.isEmpty()) {
            throw new InvalidLoginException("O login n�o pode ser vazio");
        }
        else if (login.length() > MAX_LOGIN_LENGTH) {
            throw new InvalidLoginException("O login n�o pode conter mais que 12 caracteres");
        }        
        
        return true;
    }
    
    private boolean validatePassword(String password) throws InvalidPasswordException {
            
        if (password.length() > MAX_PASSWORD_LENGTH) {
            final String message = String.format("A senha n�o pode ter mais que %d caracteres", MAX_PASSWORD_LENGTH);
            throw new InvalidPasswordException(message);
        }
        else if (password.length() > MIN_PASSWORD_LENGTH) {
            final String message = String.format("A senha n�o pode ter menos que %d caracteres", MIN_PASSWORD_LENGTH);
            throw new InvalidPasswordException(message);
        }
        else if (!password.matches("^((\\D*\\d+){2}.*)$")) {            
            throw new InvalidPasswordException("A senha deve possuir pelo menos 2 digitos num�ricos");
        }
                
        return true;
    }
    
    public class InvalidLoginException extends Exception {
		private static final long serialVersionUID = 701964896405851214L;

		public InvalidLoginException(String message) {
            super(message);
        }
        
    }
    
    public class InvalidPasswordException extends Exception {
		private static final long serialVersionUID = -8627830860280404690L;

		public InvalidPasswordException(String message) {
            super(message);
        }
        
    }
    
}
