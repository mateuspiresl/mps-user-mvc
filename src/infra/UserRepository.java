package infra;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Set;

import business.model.User;

public class UserRepository {
     
	private static UserRepository instance;
    private HashMap<String, User> dataSet;
    
    private UserRepository() {
    	instance = this;
    }
    
    public static UserRepository getInstance() {
    	return instance;
    }
    
    public static UserRepository create() {
    	instance = new UserRepository();
    	instance.dataSet = new HashMap<>();
        return instance;
    }
    
    public static UserRepository load() throws PersistOperationException {
        try {            
            FileInputStream fin = new FileInputStream("users.bin");
            ObjectInputStream objIn = new ObjectInputStream(fin);
            
            instance = new UserRepository();
            instance.dataSet = (HashMap<String, User>) objIn.readObject();
            
            objIn.close();
        } 
        catch(IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();    
            throw new PersistOperationException("Erro ao tentar recuperar informações");            
        }       
        
        return instance;
    }
    
    public void addUser(User user) throws UserAlreadyAddedException {
        if (this.dataSet.containsKey(user.getLogin())) {
            throw new UserAlreadyAddedException("Usuário existente");
        } else {
            this.dataSet.put(user.getLogin(), user);            
        }        
    }        
        
    public void deleteUser(String login) throws UserNotFoundException {
        if (this.dataSet.containsKey(login)) {
            this.dataSet.remove(login);            
        } else {
            throw new UserNotFoundException("Usuário não encontrado");
        }        
    }
    
    public Set<String> list() {
    	return this.dataSet.keySet();
    }
    
    public boolean persist() throws PersistOperationException {
        
        try {            
            FileOutputStream fout = new FileOutputStream("users.bin");
            ObjectOutputStream objOut = new ObjectOutputStream(fout);
                
            objOut.writeObject(dataSet);
            
            fout.close();
            objOut.close();
        } 
        catch(IOException ioe) {
            ioe.printStackTrace();    
            throw new PersistOperationException("Erro ao tentar salvar informações");            
        }       
        
        return true;
    }
      
    public static class PersistOperationException extends Exception {
		private static final long serialVersionUID = -625844877379088221L;

		public PersistOperationException(String message) {
            super(message);
        }
    }
    
    public static class LoadOperationException extends Exception {
		private static final long serialVersionUID = -7835192279391548467L;

		public LoadOperationException(String message) {
            super(message);
        }
    }
    
    public static class UserAlreadyAddedException extends Exception {
		private static final long serialVersionUID = -4602925985901144587L;

		public UserAlreadyAddedException(String message) {
            super(message);
        }
    }
        
    public static class UserNotFoundException extends Exception {
		private static final long serialVersionUID = 6790526783586000577L;

		public UserNotFoundException(String message) {
            super(message);
        }
    }
    
}
