package infra;

import java.util.Collections;
import java.util.List;

import business.model.Message;
import exceptions.PersistOperationException;

public class WallRepository implements RepositoryPersistence
{	
    private final List<Message> data;
    private final RepositoryPersistence persistence;
    
    WallRepository(List<Message> data, RepositoryPersistence persistence)
    {
    	this.data = data;
    	this.persistence = persistence;
    }
    
    public void add(Message message) {
        this.data.add(message);
    }
     
    public List<Message> list() {
    	return Collections.unmodifiableList(this.data);
    }
    
    @Override
    public void persist() throws PersistOperationException {
    	this.persistence.persist();
    }
}
