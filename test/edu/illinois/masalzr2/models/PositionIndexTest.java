package edu.illinois.masalzr2.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PositionIndexTest {

	PositionIndex simple;
	PositionIndex listed;
	PositionIndex complex;
	
	@Before
	public void beforeAll() {
		
		int[] x = {0,1,2,3,4};
		int[] y = {5,6,7,8,9};
		
		int[] spx = {20,21};
		int[] spy = {22,23};
		
		simple = new PositionIndex();
		
		listed = new PositionIndex(x,y);
		
		complex = new PositionIndex(x,y,spx,spy);
	}
	
	@Test
	public void testPositionIndexDefaultConstructor() {
		assertNotNull(simple);
	}

	@Test
	public void testPositionIndexXYConstructor() {
		assertNotNull(listed);
	}

	@Test
	public void testPositionIndexSpecialCaseConstructor() {
		assertNotNull(complex);
	}

	@Test
	public void testMoveOne() {
		int[] coords = listed.moveOne();
		assertArrayEquals(new int[] {1,6}, coords);
		
		listed = new PositionIndex(new int[] {0,1,2}, new int[] {3,4,5,6,7});
		
		coords = listed.getCoords();
		assertArrayEquals(new int[] {0,3}, coords);
		coords = listed.moveOne();
		assertArrayEquals(new int[] {1,4}, coords);
		coords = listed.moveOne();
		assertArrayEquals(new int[] {2,5}, coords);
		coords = listed.moveOne();
		assertArrayEquals(new int[] {0,3}, coords);
		
	}

	@Test
	public void testMove() {
		int[] coords = listed.move(5);
		assertArrayEquals(new int[] {0,5}, coords);
	}

	@Test
	public void testGetCoords() {
		assertArrayEquals(new int[] {0,5}, listed.getCoords());
	}

	@Test
	public void testStepCount() {
		assertEquals(1, simple.stepCount());
		assertEquals(5, listed.stepCount());
		assertEquals(5, listed.stepCount());
		
		simple = new PositionIndex(new int[] {0,1,2}, new int[] {3,4,5,6,7});
		listed = new PositionIndex(new int[] {3,4,5,6,7}, new int[] {0,1,2});
		
		assertEquals(3, simple.stepCount());
		assertEquals(3, listed.stepCount());
	}

	@Test
	public void testGetCoordsAtStep() {
		assertArrayEquals(new int[] {0,0}, simple.getCoordsAtStep(0));
		assertArrayEquals(new int[] {-1,-1}, simple.getCoordsAtStep(1));
		
		assertArrayEquals(new int[] {0,5}, listed.getCoordsAtStep(0));
		assertArrayEquals(new int[] {1,6}, listed.getCoordsAtStep(1));
		assertArrayEquals(new int[] {2,7}, listed.getCoordsAtStep(2));
		assertArrayEquals(new int[] {3,8}, listed.getCoordsAtStep(3));
		assertArrayEquals(new int[] {4,9}, listed.getCoordsAtStep(4));
		
		assertArrayEquals(new int[] {0,5}, complex.getCoordsAtStep(0));
		assertArrayEquals(new int[] {1,6}, complex.getCoordsAtStep(1));
		assertArrayEquals(new int[] {2,7}, complex.getCoordsAtStep(2));
		assertArrayEquals(new int[] {3,8}, complex.getCoordsAtStep(3));
		assertArrayEquals(new int[] {4,9}, complex.getCoordsAtStep(4));
		
		simple = new PositionIndex(new int[] {0,1,2}, new int[] {3,4,5,6,7});
		listed = new PositionIndex(new int[] {3,4,5,6,7}, new int[] {0,1,2});
		
		assertArrayEquals(new int[] {0,3}, simple.getCoordsAtStep(0));
		assertArrayEquals(new int[] {3,0}, listed.getCoordsAtStep(0));
		
		assertArrayEquals(new int[] {-1,-1}, simple.getCoordsAtStep(4));
		assertArrayEquals(new int[] {-1,-1}, listed.getCoordsAtStep(4));
		
	}

	@Test
	public void testGetSpecialCase() {
		assertArrayEquals(new int[] {0,0}, simple.getSpecialCase(0));
		assertArrayEquals(new int[] {-1,-1}, simple.getSpecialCase(1));
		
		assertArrayEquals(new int[] {0,0}, listed.getSpecialCase(0));
		assertArrayEquals(new int[] {-1,-1}, listed.getSpecialCase(1));
		
		assertArrayEquals(new int[] {20,22}, complex.getSpecialCase(0));
		assertArrayEquals(new int[] {21,23}, complex.getSpecialCase(1));
		
		simple = new PositionIndex(new int[] {0,1,2}, new int[] {3,4,5,6,7}, new int[] {20}, new int[] {21,22,23});
		complex = new PositionIndex(new int[] {0,1,2}, new int[] {3,4,5,6,7}, new int[] {21,22,23}, new int[] {20});
		
		assertArrayEquals(new int[] {20,21}, simple.getSpecialCase(0));
		assertArrayEquals(new int[] {-1,-1}, simple.getSpecialCase(1));
		
		assertArrayEquals(new int[] {21,20}, complex.getSpecialCase(0));
		assertArrayEquals(new int[] {-1,-1}, complex.getSpecialCase(1));
		
	}

	@Test
	public void testGetStep() {
		assertEquals(0, listed.getStep());
		listed.moveOne();
		assertEquals(1, listed.getStep());
		listed.move(2);
		assertEquals(3, listed.getStep());
		listed.move(5);
		assertEquals(3, listed.getStep());
	}

	@Test
	public void testSetStep() {
		testGetStep();
		
		listed.setStep(3);
		assertEquals(3, listed.getStep());
		
		listed.setStep(0);
		assertEquals(0, listed.getStep());
	}

	@Test
	public void testIsLocked() {
		assertFalse(simple.isLocked());
		assertFalse(listed.isLocked());
		assertFalse(complex.isLocked());
	}

	@Test
	public void testSetLocked() {
		testIsLocked();
		
		simple.setLocked(true);
		listed.setLocked(true);
		complex.setLocked(true);
		
		assertTrue(simple.isLocked());
		assertTrue(listed.isLocked());
		assertTrue(complex.isLocked());
		
	}

}
