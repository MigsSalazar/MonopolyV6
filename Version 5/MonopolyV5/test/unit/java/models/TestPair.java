package unit.java.models;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.models.Pair;

public class TestPair {

	@Test
	public void testPair() {
		Pair<Object,Object> pair = new Pair<Object,Object>(4,5);
		assertEquals(4, pair.first);
		assertEquals(5, pair.second);
		
		pair.first = this;
		Object obj = new Object();
		pair.second = obj;
		
		assertEquals(this, pair.first);
		assertEquals(obj, pair.second);
	}

}
