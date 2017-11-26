package view;

import java.util.Scanner;

import business.BusinessFacade;
import exceptions.ActionCanceledException;
import exceptions.PersistOperationException;
import exceptions.WallExistsException;
import util.Menu;

public class MainView
{
    Scanner in;
    BusinessFacade facade = new BusinessFacade();
    
    public void show()
    {
    	this.in = new Scanner(System.in);
    	
        try {
        	typeMenu();
        	mainMenu();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        closeIn();
    }
    
    private void closeIn()
    {
    	if (this.in != null)
    	{
    		this.in.close();
    		this.in = null;
    	}
    }
    
    private void typeMenu() throws PersistOperationException
    {
    	int option = new Menu(this.in, "Base de dados")
    			.option("Criar nova").option("Carregar existente").exit().show();
        
        switch (option)
        {
            case 1: this.facade.create(); break;
            case 2: this.facade.load(); break;
            case 0: return;
        }
    }
    
    private void mainMenu()
    {
    	int option = new Menu(this.in, "Menu pricipal")
    			.option("Criar mural").option("Abrir mural").option("Gerenciar usuários")
    			.exit().show();
    	
    	try {
    		switch (option)
    		{
    		case 1: WallView.createWall(this); break;
    		case 2: WallView.openWall(this); break;
    		case 3: new UserView(this).menu(); break;
    		case 0: return;
    		}    		
    	}
    	catch (WallExistsException | ActionCanceledException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	mainMenu();
    }
}
