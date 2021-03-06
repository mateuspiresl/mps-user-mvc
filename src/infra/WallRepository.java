package infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import business.model.Wall;
import exceptions.PersistOperationException;
import exceptions.WallExistsException;
import exceptions.WallNotFoundException;

public class WallRepository implements RepositoryPersistence
{	
    private final Map<String, Wall> data;
    private final RepositoryPersistence persistence;
    
    WallRepository(Map<String, Wall> data, RepositoryPersistence persistence)
    {
    	this.data = data;
    	this.persistence = persistence;
    }
    
    public void add(Wall wall) throws WallExistsException
    {
    	if (data.containsKey(wall.getName()))
    		throw new WallExistsException();
    		
        this.data.put(wall.getName(), wall);
    }
    
    public Wall get(String name) throws WallNotFoundException
    {
    	if (!has(name)) throw new WallNotFoundException();
    	return this.data.get(name);
    }
    
    public boolean has(String name) {
    	return this.data.containsKey(name);
    }
    
    public Wall remove(String name) throws WallNotFoundException
    {
    	Wall removed = this.data.remove(name);
    	
    	if (removed == null) throw new WallNotFoundException();
    	return removed;
    }
    
    public List<String> list() {
    	return new ArrayList<String>(this.data.keySet());
    }
    
    @Override
    public void persist() throws PersistOperationException {
    	this.persistence.persist();
    }
}
