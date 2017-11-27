package business.control;

import static org.junit.Assert.fail;

import org.junit.Test;

import business.control.UserManager;
import business.model.User;
import exceptions.InvalidLoginException;
import exceptions.InvalidPasswordException;
import exceptions.UserAlreadyAddedException;
import exceptions.UserNotFoundException;
import infra.RepositoryFactory;

public class UserManagerTest
{
	@Test
	public void testUserLogin() throws Exception
	{
		RepositoryFactory.reset();
		UserManager users = new UserManager(RepositoryFactory.create().getUserRepository());
		String validPassword = "aaaabbbb12";
		
		// Minimum length
		users.add(new User("a", validPassword));
		// Maximum length
		users.add(new User("aaaaabbbbbcc", validPassword));
		
		// Numeric characters
		try {
			users.add(new User("a1", validPassword));
			fail("Should throw an exception");
		} catch (InvalidLoginException e) { }
		
		// Empty login
		try {
			users.add(new User("", validPassword));
			fail("Should throw an exception");
		} catch (InvalidLoginException e) { }
		
		// Too long
		try {
			users.add(new User("aaaaabbbbbccc", validPassword));
			fail("Should throw an exception");
		} catch (InvalidLoginException e) { }
		
		// Existent user
		try {
			users.add(new User("a", validPassword));
			fail("Should throw an exception");
		} catch (UserAlreadyAddedException e) { }
	}
	
	@Test
	public void testUserPassword() throws Exception
	{
		RepositoryFactory.reset();
		UserManager users = new UserManager(RepositoryFactory.create().getUserRepository());
		String validLogin = "";
		
		// Minimum length
		users.add(new User(validLogin += "a", "aaabbb12"));
		// Maximum length
		users.add(new User(validLogin += "a", "aaaaabbbbbccccc12345"));
		
		// Too long
		try {
			users.add(new User(validLogin += "a", "aaaaabbbbbccccc123456"));
			fail("Should throw an exception");
		} catch (InvalidPasswordException e) { }
		
		// Too short
		try {
			users.add(new User(validLogin += "a", "aaabb12"));
			fail("Should throw an exception");
		} catch (InvalidPasswordException e) { }
		
		// Without numbers enough
		try {
			users.add(new User(validLogin += "a", "aaaaabbbbb1"));
			fail("Should throw an exception");
		} catch (InvalidPasswordException e) { }
	}
	
	@Test
	public void testDeleteUser() throws Exception
	{
		RepositoryFactory.reset();
		UserManager users = new UserManager(RepositoryFactory.create().getUserRepository());
		String validPassword = "aaaabbbb12";
		
		users.add(new User("a", validPassword));
		users.add(new User("b", validPassword));
		
		// Users should exist
		try {
			users.add(new User("a", validPassword));
			users.add(new User("b", validPassword));
			fail("Should throw an exception");
		} catch (UserAlreadyAddedException e) { }
		
		users.delete("a");
		
		// User 'a' should not exist
		try {
			users.delete("a");
			fail("Should throw an exception");
		} catch (UserNotFoundException e) { }
		
		users.delete("b");
		
		// Users should not exist
		try {
			users.delete("a");
			users.delete("b");
			fail("Should throw an exception");
		} catch (UserNotFoundException e) { }
		
		// Should be able to add again
		users.add(new User("a", validPassword));
		users.add(new User("b", validPassword));
	}
	
	@Test
	public void testPersistense() throws Exception
	{
		String validPassword = "aaaabbbb12";
		UserManager users;
		
		RepositoryFactory.reset();
		users = new UserManager(RepositoryFactory.create().getUserRepository());
		
		users.add(new User("a", validPassword));
		users.add(new User("b", validPassword));
		users.save();
		
		RepositoryFactory.reset();
		users = new UserManager(RepositoryFactory.load().getUserRepository());
		
		// Users should exist
		try {
			users.add(new User("a", validPassword));
			users.add(new User("b", validPassword));
			fail("Should throw an exception");
		} catch (UserAlreadyAddedException e) { }
		
		users.delete("a");
		users.save();
		
		RepositoryFactory.reset();
		users = new UserManager(RepositoryFactory.load().getUserRepository());
		
		// User 'a' should not exist
		users.add(new User("a", validPassword));
		
		// User 'b' should exist
		try {
			users.add(new User("b", validPassword));
			fail("Should throw an exception");
		} catch (UserAlreadyAddedException e) { }
	}
}
