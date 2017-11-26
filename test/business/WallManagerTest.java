package business;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import business.control.WallManager;
import business.model.CommonMessage;
import business.model.Message;
import business.model.VipMessage;
import infra.RepositoryFactory;

public class WallManagerTest
{
	@Test
	public void test() throws Exception
	{
		RepositoryFactory.reset();
		WallManager wall = new WallManager(RepositoryFactory.create().getWallRepository());
		
		String u1 = "Jão", u2 = "José";
		String t1 = "A message here", t2 = "Another here";
		Message m1 = new CommonMessage(u1, t1);
		Message m2 = new VipMessage(u2, t2);
		
		wall.add(m1);
		assertEquals(1, wall.list().size());
		
		wall.add(m2);
		assertEquals(2, wall.list().size());
		assertEquals(m1, wall.list().get(0));
		assertEquals(m2, wall.list().get(1));
	}
	
	@Test
	public void testPersistense() throws Exception
	{
		RepositoryFactory.reset();
		WallManager wall = new WallManager(RepositoryFactory.create().getWallRepository());
		wall.save();
		
		String u1 = "Jão", u2 = "José";
		String t1 = "A message here", t2 = "Another here";
		Message m1 = new CommonMessage(u1, t1);
		Message m2 = new VipMessage(u2, t2);
		
		System.out.println("Adding a message, size should be 1");
		wall.add(m1);
		assertEquals(1, wall.list().size());
		
		System.out.println("Repository unload");
		RepositoryFactory.reset();
		wall = new WallManager(RepositoryFactory.load().getWallRepository());
		
		System.out.println("Adding a message, size should be 1");
		wall.add(m2);
		assertEquals(1, wall.list().size());
		
		wall.save();
		
		RepositoryFactory.reset();
		wall = new WallManager(RepositoryFactory.load().getWallRepository());
		
		wall.add(m1);
		assertEquals(2, wall.list().size());
	}
}
