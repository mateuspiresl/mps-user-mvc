package business;

import static org.junit.Assert.*;

import org.junit.Test;

import business.model.Wall;
import exceptions.WallExistsException;

public class BusinessFacadeTest
{
	@Test
	public void testWalls() throws Exception
	{
		BusinessFacade bf = new BusinessFacade(BusinessFacade.CREATE);
		String wallName = "wall";
		String wallNewName = "wall_edited";
		
		bf.wallService(BusinessFacade.WALL_CREATE, new Wall(wallName));
		assertEquals(1, bf.listWalls().size());
		assertTrue(bf.hasWallUndo());
		
		try {
			bf.wallService(BusinessFacade.WALL_CREATE, new Wall(wallName));
			throw new Exception("Should have thown an exception");
		}
		catch (WallExistsException e) { }
		
		bf.wallService(BusinessFacade.WALL_UPDATE, new String[] { wallName, wallNewName, null });
		assertEquals(wallNewName, bf.listWalls().get(0));
		assertTrue(bf.hasWallUndo());
		
		bf.wallUndo();
		assertEquals(wallName, bf.listWalls().get(0));
		assertFalse(bf.hasWallUndo());
		
		bf.wallService(BusinessFacade.WALL_CREATE, new Wall(wallNewName));
		assertEquals(2, bf.listWalls().size());
		assertTrue(bf.hasWallUndo());
		
		bf.wallUndo();
		assertEquals(1, bf.listWalls().size());
		assertEquals(wallName, bf.listWalls().get(0));
		assertFalse(bf.hasWallUndo());
		
		bf.wallService(BusinessFacade.WALL_REMOVE, wallName);
		assertEquals(0, bf.listWalls().size());
		assertTrue(bf.hasWallUndo());
		
		bf.wallUndo();
		assertEquals(1, bf.listWalls().size());
		assertFalse(bf.hasWallUndo());
	}
}
