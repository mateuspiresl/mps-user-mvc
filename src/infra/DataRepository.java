package infra;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import business.model.User;
import business.model.Wall;
import exceptions.PersistOperationException;
import util.FileInputAdapter;
import util.FileOutputAdapter;

public class DataRepository implements RepositoryPersistence, RepositoryProvider
{
	private static final String FILE_NAME = "data.bin";
	private static DataRepository instance = null;
	
    private Data data;
    
    DataRepository(Data data)
    {
    	if (instance != null)
    		throw new RuntimeException("Can not instantiate DataRepository more than once");
    	
    	this.data = data;
    	instance = this;
    }
    
    DataRepository() {
    	this(new Data());
    }
    
    static DataRepository getInstance() {
    	return instance;
    }
    
    @ForTestsOnly
    static void reset() {
    	instance = null;
    }
    
	static DataRepository load() throws PersistOperationException
    {
        try (FileInputAdapter in = new FileInputAdapter(FILE_NAME)) {
            return new DataRepository((Data) in.read());
        } 
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();    
            throw new PersistOperationException("Erro ao tentar recuperar informações");            
        }
    }
    
	@Override
    public void persist() throws PersistOperationException
    {
        try (FileOutputAdapter out = new FileOutputAdapter(FILE_NAME)) {
        	out.write(this.data);
        } 
        catch(IOException ioe) {
            ioe.printStackTrace();    
            throw new PersistOperationException("Erro ao tentar salvar informações");
        }
    }
    
	@Override
    public UserRepository getUserRepository() {
    	return new UserRepository(this.data.users, this);
    }
    
	@Override
    public WallRepository getWallRepository() {
    	return new WallRepository(this.data.walls, this);
    }
    
    private static class Data implements Serializable
    {
		private static final long serialVersionUID = 2857304693211579433L;
		
		private HashMap<String, User> users;
    	private HashMap<String, Wall> walls;
    	
    	Data()
    	{
    		this.users = new HashMap<>();
    		this.walls = new HashMap<>();
    	}
    }
}
