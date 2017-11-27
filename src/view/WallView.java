package view;

import java.util.List;

import business.BusinessFacade;
import business.model.Message;
import exceptions.ActionCanceledException;
import exceptions.InvalidMessageException;
import exceptions.InvalidPasswordException;
import exceptions.PersistOperationException;
import exceptions.UserNotFoundException;
import exceptions.WallException;
import exceptions.WallNotFoundException;
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
	
	public static void createWall(MainView parent) throws WallException
	{
		System.out.print("\nCriar mural (entre 'x' para sair):\n\tNome: ");
		String name = null;
		
		while (name == null || name.isEmpty())
			name = parent.in.nextLine();
		
		if (name.trim().toLowerCase().equals("x")) throw new ActionCanceledException();
    	
    	parent.facade.wallService(BusinessFacade.WALL_CREATE, name);
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
	
	private void menu()
    {
		Menu menu = new Menu(this.parent.in, "Menu do mural " + this.wall)
    			.option("Ver mensagens").option("Adicionar mensagem")
    			.option("Editar mural").option("Apagar mural");
		
		if (this.parent.facade.hasWallUndo())
			menu.option("Desfazer última ação");
		
    	int option = menu.exit().show();
        
    	try {
    		switch (option)
    		{
    		case 1: listMessages(); break;
    		case 2: createMessage(); break;
    		case 3: updateWall(); break;
    		case 4: removeWall(); break;
    		case 5: undo(); break;
    		case 0: return;
    		}
    	}
    	catch (InvalidMessageException | PersistOperationException | ActionCanceledException
    			| UserNotFoundException | InvalidPasswordException | WallException e) {
    		System.out.println(e.getMessage());
    	}
        
        menu();
    }
	
	private void updateWall() throws WallException
	{
		System.out.println(String.format("Editando o mural %s (enter 'x' to ignore a field):", this.wall));
		String name, description;
		
		System.out.print("\n\tNome: ");
		do name = this.parent.in.next(); while (name.isEmpty());
		if (name.trim().toLowerCase().equals("x")) name = null;
		
		System.out.print("\n\tDescrição: ");
		do description = this.parent.in.next(); while (description.isEmpty());
		if (description.trim().toLowerCase().equals("x")) description = null;
		
		if (name == null && description == null) throw new ActionCanceledException();
		
		this.parent.facade.wallService(BusinessFacade.WALL_UPDATE, new String[] {
				this.wall, name, description
		});
	}
	
	private void removeWall() throws WallException {
		this.parent.facade.wallService(BusinessFacade.WALL_REMOVE, this.wall);
	}
	
	private void undo() throws WallException {
		this.parent.facade.wallUndo();
	}
	
	private void listMessages() throws WallNotFoundException
	{
		List<Message> messages = this.parent.facade.listMessages(this.wall);
		
		if (messages.size() > 0) {
			System.out.println("\nMensagens:");
			messages.forEach(message -> System.out.println(message));
		}
		else {
			System.out.println("\nMensagens: VAZIO.");
		}
	}
	
	private void createMessage() throws InvalidMessageException, PersistOperationException, UserNotFoundException,
		InvalidPasswordException, ActionCanceledException, WallNotFoundException
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
