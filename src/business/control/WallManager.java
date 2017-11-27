package business.control;

import java.util.List;

import business.model.Message;
import business.model.Wall;
import exceptions.InvalidMessageException;
import exceptions.PersistOperationException;
import exceptions.WallExistsException;
import exceptions.WallNotFoundException;
import infra.WallRepository;

public class WallManager
{
	public static final int MAX_MESSAGE_LENGTH = 128;
            
    private WallRepository repository;
    
    public WallManager(WallRepository repository) {
        this.repository = repository;
    }
    
    public void addWall(Wall wall) throws WallExistsException {
    	this.repository.add(wall);
    }
    
    public Wall getWall(String name) throws WallNotFoundException {
    	return this.repository.get(name);
    }
    
    public boolean hasWall(String name) {
    	return this.repository.has(name);
    }
    
    public void updateWall(String which, String name, String description) throws WallNotFoundException
    {
    	if (name == null && description == null) return;
    	
    	Wall wall = this.repository.remove(which);
    	
    	if (name != null) wall.setName(name);
    	if (description != null) wall.setDescription(description);
    	
    	try { this.repository.add(wall); }
    	catch (WallExistsException e) { }
    }
    
    public Wall removeWall(String name) throws WallNotFoundException {
    	return this.repository.remove(name);
    }
    
    public void addMessage(String wall, final Message message) throws InvalidMessageException, WallNotFoundException
    {
    	validateMessage(message);
    	this.repository.get(wall).add(message);
    }
    
    public List<String> listWalls() {
    	return this.repository.list();
    }
    
    public List<Message> listMessages(String wall) throws WallNotFoundException {
    	return this.repository.get(wall).list();
    }
    
    public void save() throws PersistOperationException {
    	this.repository.persist();
    }
    
    private void validateMessage(Message message) throws InvalidMessageException
    {
        if (message.getText().isEmpty())
            throw new InvalidMessageException("A mensagem não pode ser vazia");
        
        else if (message.getText().length() > MAX_MESSAGE_LENGTH)
            throw new InvalidMessageException(
            		String.format("A mensagem não pode conter mais que %d caracteres", MAX_MESSAGE_LENGTH));
    }
}
