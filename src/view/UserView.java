package view;

import exceptions.ActionCanceledException;
import exceptions.InvalidLoginException;
import exceptions.InvalidPasswordException;
import exceptions.PersistOperationException;
import exceptions.UserAlreadyAddedException;
import exceptions.UserNotFoundException;
import util.Menu;

public class UserView
{
	private MainView parent;
	
	public UserView(MainView parent) {
		this.parent = parent;
	}
	
	public void menu()
    {
    	int option = new Menu(this.parent.in, "Menu de Usuários")
    			.option("Adicionar").option("Remover")
    			.exit().show();
    	
    	try {
    		switch (option)
            {
                case 1: create(); break; 
                case 2: remove(); break;
                case 0: return;
                default: System.out.println("Opção inválida.");
            }
    	}
    	catch (PersistOperationException | UserNotFoundException | ActionCanceledException e) {
			System.out.println(e.getMessage());
		}
    	
        menu();
    }
	
	public void create(String login) throws ActionCanceledException
    {
    	if (login == null)
    	{
    		System.out.print("\nCriar usuário (entre 'x' para sair):\n\tLogin: ");
    		login = this.parent.in.next();
    		if (login.trim().toLowerCase().equals("x")) throw new ActionCanceledException();
    	}
    	    	
		System.out.print("\tPassword: ");
		String password = this.parent.in.next();
		if (password.trim().toLowerCase().equals("x")) throw new ActionCanceledException();
    	
    	try {
    		this.parent.facade.addUser(login, password);
    	}
    	catch (InvalidLoginException | UserAlreadyAddedException e) {
    		System.out.println(e.getMessage());
    		this.create(null);
    	}
    	catch (InvalidPasswordException e) {
    		System.out.println(e.getMessage());
    		this.create(login);
    	}
    }
    
    public void create() {
    	this.create(null);
    }
    
    public void remove() throws UserNotFoundException, ActionCanceledException
    {
    	System.out.print("\nRemover usuário (entre 'x' para sair):\n\tLogin: ");
		String login = this.parent.in.next();
		if (login.trim().toLowerCase().equals("x")) throw new ActionCanceledException();
    	
    	this.parent.facade.deleteUser(login);
    }
    
    public String[] getCredencials() throws ActionCanceledException
    {
		System.out.print("\nLogin (entre 'x' para sair):\n\tNome: ");
		String login = this.parent.in.next();
		if (login.trim().toLowerCase().equals("x")) throw new ActionCanceledException();
    	
		System.out.print("\tPassword: ");
		String password = this.parent.in.next();
		if (password.trim().toLowerCase().equals("x")) throw new ActionCanceledException();
    	
    	return new String[] { login, password };
    }
}
