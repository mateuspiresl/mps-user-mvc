package business;

import static org.junit.Assert.*;

import org.junit.Test;

import business.control.UserManager;
import business.control.UserManager.InvalidLoginException;
import business.control.UserManager.InvalidPasswordException;
import business.model.User;
import infra.UserRepository;
import infra.UserRepository.PersistOperationException;
import infra.UserRepository.UserAlreadyAddedException;
import infra.UserRepository.UserNotFoundException;

public class UserManagerTest
{
	@Test
	public void testUserLogin() throws InvalidLoginException, InvalidPasswordException, UserAlreadyAddedException
	{
		UserManager users = new UserManager(UserRepository.create());
		String validPassword = "aaaabbbb12";
		
		// Minimum length
		users.addUser(new User("a", validPassword));
		// Maximum length
		users.addUser(new User("aaaaabbbbbcc", validPassword));
		
		// Numeric characters
		try {
			users.addUser(new User("a1", validPassword));
			fail("Should throw an exception");
		} catch (InvalidLoginException e) { }
		
		// Empty login
		try {
			users.addUser(new User("", validPassword));
			fail("Should throw an exception");
		} catch (InvalidLoginException e) { }
		
		// Too long
		try {
			users.addUser(new User("aaaaabbbbbccc", validPassword));
			fail("Should throw an exception");
		} catch (InvalidLoginException e) { }
		
		// Existent user
		try {
			users.addUser(new User("a", validPassword));
			fail("Should throw an exception");
		} catch (UserAlreadyAddedException e) { }
	}
	
	@Test
	public void testUserPassword() throws InvalidLoginException, InvalidPasswordException, UserAlreadyAddedException
	{
		UserManager users = new UserManager(UserRepository.create());
		String validLogin = "";
		
		// Minimum length
		users.addUser(new User(validLogin += "a", "aaabbb12"));
		// Maximum length
		users.addUser(new User(validLogin += "a", "aaaaabbbbbccccc12345"));
		
		// Too long
		try {
			users.addUser(new User(validLogin += "a", "aaaaabbbbbccccc123456"));
			fail("Should throw an exception");
		} catch (InvalidPasswordException e) { }
		
		// Too short
		try {
			users.addUser(new User(validLogin += "a", "aaabb12"));
			fail("Should throw an exception");
		} catch (InvalidPasswordException e) { }
		
		// Without numbers enough
		try {
			users.addUser(new User(validLogin += "a", "aaaaabbbbb1"));
			fail("Should throw an exception");
		} catch (InvalidPasswordException e) { }
	}
	
	@Test
	public void testDeleteUser() throws InvalidLoginException, InvalidPasswordException, UserAlreadyAddedException, UserNotFoundException
	{
		UserManager users = new UserManager(UserRepository.create());
		String validPassword = "aaaabbbb12";
		
		users.addUser(new User("a", validPassword));
		users.addUser(new User("b", validPassword));
		
		// Users should exist
		try {
			users.addUser(new User("a", validPassword));
			users.addUser(new User("b", validPassword));
			fail("Should throw an exception");
		} catch (UserAlreadyAddedException e) { }
		
		users.deleteUser("a");
		
		// User 'a' should not exist
		try {
			users.deleteUser("a");
			fail("Should throw an exception");
		} catch (UserNotFoundException e) { }
		
		users.deleteUser("b");
		
		// Users should not exist
		try {
			users.deleteUser("a");
			users.deleteUser("b");
			fail("Should throw an exception");
		} catch (UserNotFoundException e) { }
		
		// Should be able to add again
		users.addUser(new User("a", validPassword));
		users.addUser(new User("b", validPassword));
	}
	
	@Test
	public void testPersistense() throws InvalidLoginException, InvalidPasswordException, UserAlreadyAddedException, PersistOperationException, UserNotFoundException
	{
		String validPassword = "aaaabbbb12";
		UserManager users;
		
		users = new UserManager(UserRepository.create());
		
		users.addUser(new User("a", validPassword));
		users.addUser(new User("b", validPassword));
		users.save();
		
		users = new UserManager(UserRepository.load());
		
		// Users should exist
		try {
			users.addUser(new User("a", validPassword));
			users.addUser(new User("b", validPassword));
			fail("Should throw an exception");
		} catch (UserAlreadyAddedException e) { }
		
		users.deleteUser("a");
		users.save();
		
		users = new UserManager(UserRepository.load());
		
		// User 'a' should not exist
		users.addUser(new User("a", validPassword));
		
		// User 'b' should exist
		try {
			users.addUser(new User("b", validPassword));
			fail("Should throw an exception");
		} catch (UserAlreadyAddedException e) { }
	}
}
