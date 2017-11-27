package business;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.control.UserManager;
import business.control.WallCommands;
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
import exceptions.WallException;
import exceptions.WallNotFoundException;
import infra.RepositoryFactory;
import infra.RepositoryProvider;

public class BusinessFacade
{
	public static final boolean CREATE = false;
	public static final boolean LOAD = true;
	public static final String WALL_CREATE = "create";
	public static final String WALL_UPDATE = "update";
	public static final String WALL_REMOVE = "remove";
	
	private UserManager users;
	private WallManager walls;
	private WallCommands.Memento wallMemento;
	private Map<String, WallCommands.Command> wallCommands;
	
	@SuppressWarnings("serial")
	public BusinessFacade(boolean type)
	{
		RepositoryProvider repository = type == CREATE
				? RepositoryFactory.create()
				: RepositoryFactory.load();
		
		this.users = new UserManager(repository.getUserRepository());
		this.walls = new WallManager(repository.getWallRepository());
		
		this.wallCommands = new HashMap<String, WallCommands.Command>() {{
			super.put(WALL_CREATE, new WallCommands.Create(walls));
			super.put(WALL_UPDATE, new WallCommands.Update(walls));
			super.put(WALL_REMOVE, new WallCommands.Remove(walls));
		}};
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
	
	public void wallService(String command, Object data) throws WallException {
		this.wallMemento = this.wallCommands.get(command).execute(data);
	}
	
	public void wallUndo() throws WallException
	{
		this.wallMemento.call();
		this.wallMemento = null;
	}
	
	public boolean hasWallUndo() {
		return this.wallMemento != null;
	}
	
	public void addMessage(String wall, String name, String password, String message)
			throws InvalidMessageException, PersistOperationException, UserNotFoundException, InvalidPasswordException,
			WallNotFoundException
	{
		if (!this.walls.hasWall(wall)) throw new WallNotFoundException();
		
		User user = this.users.match(name, password);
		this.walls.addMessage(wall, MessageFactory.create(user, message));
		this.walls.save();
	}
	
	public List<Message> listMessages(String wall) throws WallNotFoundException {
		return this.walls.listMessages(wall);
	}
}
