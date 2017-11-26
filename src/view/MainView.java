package view;

import java.util.Scanner;

import business.BusinessFacade;
import exceptions.PersistOperationException;
import util.Menu;

public class MainView
{
    Scanner in;
    BusinessFacade facade = new BusinessFacade();
    
    public void menu()
    {
    	this.in = new Scanner(System.in);
    	
        try {
            menuBegin();
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
    
    private void menuBegin() throws PersistOperationException
    {
    	int option = new Menu(this.in, "Menu Principal")
    			.option("Criar novo").option("Carregar existente").exit().show();
        
        switch (option)
        {
            case 1: this.facade.create(); break;
            case 2: this.facade.load(); break;
            case 0: return;
        }
        
        new WallView(this).menu();
    }
}
