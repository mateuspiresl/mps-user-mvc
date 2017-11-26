package view;

import java.util.List;

import business.model.Message;
import exceptions.ActionCanceledException;
import exceptions.InvalidMessageException;
import exceptions.InvalidPasswordException;
import exceptions.PersistOperationException;
import exceptions.UserNotFoundException;
import util.Menu;

public class WallView
{
	private MainView parent;
	
	public WallView(MainView parent) {
		this.parent = parent;
	}
	
	public void menu()
    {
    	int option = new Menu(this.parent.in, "Menu do Mural")
    			.option("Ver mensagens").option("Adicionar mensagem").option("Gerenciar usuários")
    			.exit().show();
        
    	try {
    		switch (option)
    		{
    		case 1: list(); break;
    		case 2: create(); break;
    		case 3: new UserView(this.parent).menu(); break;
    		case 0: return;
    		
    		default: System.out.println("Opção inválida!");
    		}
    	}
    	catch (InvalidMessageException | PersistOperationException | UserNotFoundException | InvalidPasswordException
    			| ActionCanceledException e) {
    		System.out.println(e.getMessage());
    	}
        
        menu();
    }
	
	public void list()
	{
		List<Message> messages = this.parent.facade.listMessages();
		
		if (messages.size() > 0) {
			System.out.println("\nMural:");
			messages.forEach(message -> System.out.println(message));
		}
		else {
			System.out.println("\nMural: VAZIO.");
		}
	}
	
	public void create() throws InvalidMessageException, PersistOperationException, UserNotFoundException,
		InvalidPasswordException, ActionCanceledException
	{
		String[] credencials = new UserView(this.parent).getCredencials();
		if (credencials != null)
		{
			System.out.println("\nWrite your message: ");
			String text = null;
			
			while (text == null || text.isEmpty())
				text = this.parent.in.nextLine();
			
			if (text.trim().toLowerCase().equals("x")) throw new ActionCanceledException();
			this.parent.facade.addMessage(credencials[0], credencials[1], text);
		}
	}
}
