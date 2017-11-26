package business.control;

import java.util.List;

import business.model.Message;
import exceptions.InvalidMessageException;
import exceptions.PersistOperationException;
import infra.WallRepository;

public class WallManager
{
	public static final int MAX_MESSAGE_LENGTH = 128;
            
    private WallRepository repository;
    
    public WallManager(WallRepository repository) {
        this.repository = repository;
    }
    
    public void add(final Message message) throws InvalidMessageException
    {
    	validateMessage(message);
    	this.repository.add(message);
    }
    
    public List<Message> list() {
    	return this.repository.list();
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
