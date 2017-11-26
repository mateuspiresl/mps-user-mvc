package view;

import java.util.List;

import business.model.Message;
import exceptions.ActionCanceledException;
import exceptions.InvalidMessageException;
import exceptions.InvalidPasswordException;
import exceptions.PersistOperationException;
import exceptions.UserNotFoundException;
import exceptions.WallExistsException;
import util.Menu;

public class WallView
{
	private final MainView parent;
	private final String wall;
	
	public WallView(MainView parent, String wall)
	{
		this.parent = parent;
		this.wall = wall;
	}
	
	public static void createWall(MainView parent) throws WallExistsException
	{
		System.out.print("\nCriar mural (entre 'x' para sair):\n\tNome: ");
		String name = null;
		
		while (name == null || name.isEmpty())
			name = parent.in.nextLine();
		
		if (name.trim().toLowerCase().equals("x")) throw new ActionCanceledException();
    	
    	parent.facade.addWall(name);
	}
	
	public static void openWall(MainView parent)
	{
		List<String> walls = parent.facade.listWalls();
		
		if (walls.size() > 0)
		{
			Menu menu = new Menu(parent.in, "Escolha um mural:");
			walls.forEach(wall -> menu.option(wall));
			
			int option = menu.exit("(Nenhum)").show();
			if (option > 0) new WallView(parent, walls.get(option - 1)).menu();
		}
		else {
			System.out.println("Murais: VAZIO.");
		}
	}
	
	public void menu()
    {
    	int option = new Menu(this.parent.in, "Menu do mural " + this.wall)
    			.option("Ver mensagens").option("Adicionar mensagem")
    			.exit().show();
        
    	try {
    		switch (option)
    		{
    		case 1: list(); break;
    		case 2: create(); break;
    		case 0: return;
    		}
    	}
    	catch (InvalidMessageException | PersistOperationException | ActionCanceledException
    			| UserNotFoundException | InvalidPasswordException e) {
    		System.out.println(e.getMessage());
    	}
        
        menu();
    }
	
	public void list()
	{
		List<Message> messages = this.parent.facade.listMessages(this.wall);
		
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
			System.out.println("\nEscreva sua mensagem: ");
			String text = null;
			
			while (text == null || text.isEmpty())
				text = this.parent.in.nextLine();
			
			if (text.trim().toLowerCase().equals("x")) throw new ActionCanceledException();
			this.parent.facade.addMessage(this.wall, credencials[0], credencials[1], text);
		}
	}
}
