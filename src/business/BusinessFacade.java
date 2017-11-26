package business;


import java.util.List;

import business.control.UserManager;
import business.control.WallManager;
import business.model.Message;
import business.model.MessageFactory;
import business.model.User;
import exceptions.InvalidLoginException;
import exceptions.InvalidMessageException;
import exceptions.InvalidPasswordException;
import exceptions.PersistOperationException;
import exceptions.UserAlreadyAddedException;
import exceptions.UserNotFoundException;
import exceptions.WallExistsException;
import exceptions.WallNotFoundException;
import infra.RepositoryFactory;
import infra.RepositoryProvider;

public class BusinessFacade
{
	private UserManager users;
	private WallManager walls;
	
	private void initializeManagers(RepositoryProvider repository)
	{
		this.users = new UserManager(repository.getUserRepository());
		this.walls = new WallManager(repository.getWallRepository());
	}
	
	public void create()
	{
		if (this.users != null)
			throw new IllegalAccessError("Can not load the repository more than once");
		
		initializeManagers(RepositoryFactory.create());
	}
	
	public void load() throws PersistOperationException
	{
		if (this.users != null)
			throw new IllegalAccessError("Can not load the repository more than once");
		
		initializeManagers(RepositoryFactory.load());
	}
	
	public void addUser(String name, String password) throws InvalidLoginException, InvalidPasswordException,
			UserAlreadyAddedException, PersistOperationException
	{
		this.users.add(new User(name, password));
		this.users.save();
	}
	
	public void deleteUser(String name) throws UserNotFoundException, PersistOperationException
	{
		this.users.delete(name);
		this.users.save();
	}
	
	public List<String> listWalls() {
		return this.walls.listWalls();
	}
	
	public void addWall(String name) throws WallExistsException {
		this.walls.addWall(name);
	}
	
	public void addMessage(String wall, String name, String password, String message)
			throws InvalidMessageException, PersistOperationException, UserNotFoundException, InvalidPasswordException,
			WallNotFoundException
	{
		if (!this.walls.hasWall(name)) throw new WallNotFoundException();
		
		User user = this.users.match(name, password);
		this.walls.addMessage(wall, MessageFactory.create(user, message));
		this.walls.save();
	}
	
	public List<Message> listMessages(String wall) {
		return this.walls.listMessages(wall);
	}
}
