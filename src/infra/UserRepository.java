package infra;

import java.util.HashMap;
import java.util.Set;

import business.model.User;
import exceptions.PersistOperationException;
import exceptions.UserAlreadyAddedException;
import exceptions.UserNotFoundException;

public class UserRepository implements RepositoryPersistence
{
    private final HashMap<String, User> data;
    private final RepositoryPersistence persistence;
    
    UserRepository(HashMap<String, User> data, RepositoryPersistence persistence)
    {
    	this.data = data;
    	this.persistence = persistence;
    }
    
    public void add(User user) throws UserAlreadyAddedException
    {
        if (this.data.containsKey(user.getLogin()))
        {
        	user = this.data.get(user.getLogin());
        	System.out.println("== User exists: " + user.getLogin() + ", " + user.getPassword());
        	throw new UserAlreadyAddedException();
        }
        
        this.data.put(user.getLogin(), user);
    }        
        
    public void delete(String login) throws UserNotFoundException
    {
        if (this.data.remove(login) == null)
            throw new UserNotFoundException();
    }
    
    public User get(String login) throws UserNotFoundException
    {
    	if (this.data.containsKey(login))
    		return this.data.get(login);
    	else
            throw new UserNotFoundException();
    }
    
    public Set<String> list() {
    	return this.data.keySet();
    }
    
    @Override
    public void persist() throws PersistOperationException {
    	this.persistence.persist();
    }
}
