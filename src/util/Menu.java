package util;

import java.util.Scanner;

public class Menu
{
	private final Scanner in;
	private final StringBuilder builder;
	private final boolean retryOnError;
	private boolean hasExit;
	private int nextNumber = 1;
	
	public Menu(Scanner in, String name, boolean retryOnError)
	{
		this.in = in;
		this.retryOnError = retryOnError;
		this.builder = new StringBuilder()
				.append("\n").append(name).append("\n");
	}
	
	public Menu(Scanner in, String name) {
		this(in, name, true);
	}
	
	public Menu(Scanner in) {
		this(in, "Menu");
	}
	
	public Menu option(String name)
	{
		this.builder
				.append("\t")
				.append(this.nextNumber++)
				.append(". ")
				.append(name)
				.append("\n");
		
		return this;
	}
	
	public Menu exit(String name)
	{
		option(name);
		this.nextNumber--;
		this.hasExit = true;
		return this;
	}
	
	public Menu exit() {
		return exit("Sair");
	}
	
	public int show()
	{
		System.out.println(this.builder);
		System.out.print(">> ");
		int answer = in.nextInt();
		
		if (this.hasExit && answer == this.nextNumber) {
			return 0;
		}
		else if (answer >= this.nextNumber) {
			if (this.retryOnError)
			{
				System.out.println("Opção inválida.");
				return show();
			}
			return -1;
		}
		
		return answer;
	}
}
