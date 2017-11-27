package business.control;

import business.model.Wall;
import exceptions.WallException;

public class WallCommands
{
	public static class Create implements Command
	{
		private WallManager manager;
		
		public Create(WallManager manager) {
			this.manager = manager;
		}
		
		@Override
		public Memento execute(Object data) throws WallException
		{
			Wall wall = (Wall) data;
			this.manager.addWall(wall);
			return new Memento(new Remove(this.manager), wall.getName());
		}
	}
	
	public static class Update implements Command
	{
		private WallManager manager;
		
		public Update(WallManager manager) {
			this.manager = manager;
		}
		
		private Memento update(String which, String name, String description) throws WallException
		{
			Wall wall = this.manager.getWall(which);
			String[] mementoData = new String[] { name, which, wall.getDescription() };
			
			this.manager.updateWall(which, name, description);
			this.manager.save();
			
			return new Memento(new Update(this.manager), mementoData);
		}
		
		@Override
		public Memento execute(Object data) throws WallException
		{
			String[] params = (String[]) data;
			return update(params[0], params[1], params[2]);
		}
	}
	
	public static class Remove implements Command
	{
		private WallManager manager;
		
		public Remove(WallManager manager) {
			this.manager = manager;
		}
		
		@Override
		public Memento execute(Object data) throws WallException
		{
			Wall removed = this.manager.removeWall((String) data);
			return new Memento(new Create(this.manager), removed);
		}
	}
	
	
	public static class Memento
	{
		private Command command;
		private Object data;
		
		public Memento(Command command, Object data)
		{
			this.command = command;
			this.data = data;
		}
		
		public void call() throws WallException {
			this.command.execute(data);
		}
	}
	
	public static interface Command {
		Memento execute(Object data) throws WallException;
	}
}
