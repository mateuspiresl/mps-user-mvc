/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Scanner;
import java.util.Set;

import business.control.UserManager;
import business.control.UserManager.InvalidLoginException;
import business.control.UserManager.InvalidPasswordException;
import business.model.User;
import infra.UserRepository;
import infra.UserRepository.PersistOperationException;
import infra.UserRepository.UserNotFoundException;

/**
 *
 * @author aluno
 */
public class UserForm
{
	private UserManager users;
    private Scanner in;
    
    public void menu()
    {
    	this.in = new Scanner(System.in);
    	
        try {
            menuBegin();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            menu();
        }
    }
    
    private void menuBegin() throws UserRepository.PersistOperationException
    {
        System.out.print("\nMenu:\n\t1. Criar novo\n\t2. Carregar existente\n\t3. Sair\n>> ");
        int option = this.in.nextInt();
        
        switch (option)
        {
            case 1:
            	this.users = new UserManager(UserRepository.create());
                break;
            
            case 2:
            	this.users = new UserManager(UserRepository.load());
                break;
                
            case 3: return;
            
            default:
                System.out.println("Erro: opção inválida.");
                menuBegin();
                return;
        }
        
        menuList();
        menuBegin();
    }
    
    private void menuList()
    {
        System.out.print("\nMenu:\n\t1. Criar\n\t2. Remover\n\t3. Salvar\n\t4. Voltar\n>> ");
        int option = this.in.nextInt();
        
        switch (option)
        {
            case 1:
                createUser();
                break;
                
            case 2:
                removeUser();
                break;
                
            case 3:
				try {
					this.users.save();
				}
				catch (PersistOperationException e) {
					System.out.println("Erro: " + e.getMessage());
				}
				break;
				
            case 4:
            	return;
            	
            case 99:
            	Set<String> names = UserRepository.getInstance().list();
            	names.forEach(name -> System.out.print(name + ", "));
            	System.out.println();
            	break;
            
            default:
                System.out.println("Erro: opção inválida.");
        }
        
        menuList();
    }
    
    private void createUser(String login)
    {
    	if (login == null)
    	{
    		System.out.print("\nCreate user (enter empty to return):\n\tLogin: ");
    		login = this.in.next();
    	}
    	
    	if (login.trim().isEmpty())
    		return;
    	
		System.out.print("\tPassword: ");
		String password = this.in.next();
    	
    	try {
    		this.users.addUser(new User(login, password));
    	}
    	catch (InvalidLoginException | UserRepository.UserAlreadyAddedException e) {
    		System.out.println("Error: " + e.getMessage());
    		createUser(null);
    	}
    	catch (InvalidPasswordException e) {
    		System.out.println("Error: " + e.getMessage());
    		createUser(login);
    	}
    }
    
    private void createUser() {
    	createUser(null);
    }
    
    private void removeUser()
    {
    	System.out.print("\nRemove user (enter empty to return):\n\tLogin: ");
		String login = this.in.next();
    	
    	if (login.trim().isEmpty())
    		return;
    	
    	try {
			this.users.deleteUser(login);
		}
    	catch (UserNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		}
    }
}
