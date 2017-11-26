package business.control;

import business.model.User;
import exceptions.InvalidLoginException;
import exceptions.InvalidPasswordException;
import exceptions.PersistOperationException;
import exceptions.UserAlreadyAddedException;
import exceptions.UserNotFoundException;
import infra.UserRepository;

public class UserManager
{
    public static final int MAX_LOGIN_LENGTH = 12;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 20;
            
    private UserRepository repository;
    
    public UserManager(UserRepository repository) {
        this.repository = repository;
    }
    
    public void add(final User user) throws InvalidLoginException,
    	InvalidPasswordException, UserAlreadyAddedException
    {
    	validateLogin(user.getLogin());
    	validatePassword(user.getPassword());
        
    	this.repository.add(user);
    }
        
    public void delete(final String login) throws UserNotFoundException {
    	this.repository.delete(login);    
    }
    
    public User match(String login, String password) throws UserNotFoundException, InvalidPasswordException
    {
    	User user = this.repository.get(login);
    	
    	if (!user.getPassword().equals(password))
    		throw new InvalidPasswordException("A senha está diferente do cadastro");
    	
    	return user;
    }
    
    public void save() throws PersistOperationException {
    	this.repository.persist();
    }
    
    private void validateLogin(String login) throws InvalidLoginException
    {
        if (login.matches(".*\\d.*"))            
            throw new InvalidLoginException("O login não pode conter numéricos");
        
        else if (login.isEmpty())
            throw new InvalidLoginException("O login não pode ser vazio");
        
        else if (login.length() > MAX_LOGIN_LENGTH)
            throw new InvalidLoginException("O login não pode conter mais que 12 caracteres");
    }
    
    private void validatePassword(String password) throws InvalidPasswordException
    {
        if (password.length() > MAX_PASSWORD_LENGTH)
            throw new InvalidPasswordException(
            		String.format("A senha não pode ter mais que %d caracteres", MAX_PASSWORD_LENGTH));
        
        else if (password.length() < MIN_PASSWORD_LENGTH)
            throw new InvalidPasswordException(
            		String.format("A senha não pode ter menos que %d caracteres", MIN_PASSWORD_LENGTH));
        
        else if (!password.matches("^((\\D*\\d+){2}.*)$"))
            throw new InvalidPasswordException("A senha deve possuir pelo menos 2 digitos numéricos");
    }
}
