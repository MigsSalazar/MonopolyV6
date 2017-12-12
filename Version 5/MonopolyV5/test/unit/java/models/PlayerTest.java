package unit.java.models;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import main.java.models.*;
import java.util.*;

public class PlayerTest {
	
	public static Player freshPlayer;
	public static Player oldPlayer;
	public static Map<String, Property> properties;
	public static Property junk;
	public static Property metra;
	public static Property comcast;
	public static GlobalCounter rcount = new GlobalCounter(2,4);
	public static GlobalCounter ucount = new GlobalCounter(1,2);
	public static int[] rents = {1,2,3,4,5,6,7,8,9};
	
	@BeforeClass
	public static void beforeClassTest(){
		junk = new Colored("Junk Ave.", 13, 130, true, rents, 0 );
		metra = new Railroad("Metra", 15, 200, false, rcount);
		comcast = new Utility("Comcast", 13, 150, false, rcount);
		properties = new HashMap<String, Property>();
		properties.put("Junk Ave.", junk);
		properties.put("Metra", metra);
		properties.put("Comcast", comcast);
		
		
		oldPlayer = new Player("Paul", 6, 10, 1000, 0, 1, true, true, false, properties);
		
		freshPlayer = new Player(1, "John");
		
	}
	
	@Before
	public void beforeTest(){
		properties = new HashMap<String, Property>();
		properties.put(junk.getName(), junk);
		properties.put(metra.getName(), metra);
		properties.put(comcast.getName(), comcast);
		oldPlayer = new Player("Paul", 6, 10, 1000, 0, 1, true, true, false, properties);
		freshPlayer = new Player(1, "John");
	}

	@Test
	public void testGetUserID() {
		assertEquals(freshPlayer.getUserID(), 1);
		assertEquals(oldPlayer.getUserID(), 6);
	}

	@Test
	public void testGetName() {
		assertEquals(freshPlayer.getName(), "John");
		assertEquals(oldPlayer.getName(), "Paul");
	}

	@Test
	public void testGetPosition() {
		assertEquals(freshPlayer.getPosition(), 0);
		assertEquals(oldPlayer.getPosition(), 10);
	}

	@Test
	public void testSetPosition() {
		//beforeTest();
		freshPlayer.setPosition(7);
		oldPlayer.setPosition(15);
		assertEquals(freshPlayer.getPosition(), 7);
		assertEquals(oldPlayer.getPosition(), 15);
	}

	@Test
	public void testGetCash() {
		//beforeTest();
		assertEquals(freshPlayer.getCash(), 1500);
		assertEquals(oldPlayer.getCash(), 1000);
	}

	@Test
	public void testAddCash() {
		//beforeTest();
		freshPlayer.addCash(200);
		oldPlayer.addCash(200);
		assertEquals(freshPlayer.getCash(), 1700);
		assertEquals(oldPlayer.getCash(), 1200);
	}

	@Test
	public void testSubCash() {
		//beforeTest();
		freshPlayer.subCash(300);
		oldPlayer.subCash(300);
		assertEquals(freshPlayer.getCash(), 1200);
		assertEquals(oldPlayer.getCash(), 700);
	}

	@Test
	public void testSetCash() {
		//beforeTest();
		freshPlayer.setCash(200);
		oldPlayer.setCash(200);
		assertEquals(freshPlayer.getCash(), 200);
		assertEquals(oldPlayer.getCash(), 200);
	}

	@Test
	public void testGetWealth() {
		//beforeTest();
		//315
		assertEquals(freshPlayer.getWealth(), 1500);
		assertEquals(oldPlayer.getWealth(), 1415);
	}

	@Test
	public void testGetRedeemableWealth() {
		//beforeTest();
		assertEquals(freshPlayer.getRedeemableWealth(), 1500);
		assertEquals(oldPlayer.getRedeemableWealth(), 1175);
	}

	@Test
	public void testGetJailCards() {
		//beforeTest();
		assertEquals(freshPlayer.getJailCards(), 0);
		assertEquals(oldPlayer.getJailCards(), 0);
	}

	@Test
	public void testSetJailCards() {
		//beforeTest();
		freshPlayer.setJailCards(1);
		oldPlayer.setJailCards(10);
		assertEquals(freshPlayer.getJailCards(), 1);
		assertEquals(oldPlayer.getJailCards(), 10);
	}

	@Test
	public void testAddJailCard() {
		//beforeTest();
		freshPlayer.addJailCard();
		oldPlayer.addJailCard();
		assertEquals(freshPlayer.getJailCards(), 1);
		assertEquals(oldPlayer.getJailCards(), 1);
	}

	@Test
	public void testAddJailCardInt() {
		//beforeTest();
		freshPlayer.addJailCard(10);
		oldPlayer.addJailCard(3);
		assertEquals(freshPlayer.getJailCards(), 10);
		assertEquals(oldPlayer.getJailCards(), 3);
	}

	@Test
	public void testSubJailCard() {
		//beforeTest();
		freshPlayer.addJailCard(10);
		oldPlayer.addJailCard(10);
		freshPlayer.subJailCard();
		oldPlayer.subJailCard();
		assertEquals(freshPlayer.getJailCards(), 9);
		assertEquals(oldPlayer.getJailCards(), 9);
	}

	@Test
	public void testSubJailCardInt() {
		//beforeTest();
		freshPlayer.addJailCard(10);
		oldPlayer.addJailCard(10);
		freshPlayer.subJailCard(5);
		oldPlayer.subJailCard(3);
		assertEquals(freshPlayer.getJailCards(), 5);
		assertEquals(oldPlayer.getJailCards(), 7);
	}

	@Test
	public void testIsInJail() {
		//beforeTest();
		assertFalse(freshPlayer.isInJail());
		assertTrue(oldPlayer.isInJail());
	}

	@Test
	public void testSetInJail() {
		//beforeTest();
		freshPlayer.setInJail(true);
		oldPlayer.setInJail(false);
		assertTrue(freshPlayer.isInJail());
		assertFalse(oldPlayer.isInJail());
	}

	@Test
	public void testToggelInJail() {
		//beforeTest();
		assertTrue(freshPlayer.toggelInJail());
		assertFalse(oldPlayer.toggelInJail());
		assertFalse(freshPlayer.toggelInJail());
		assertTrue(oldPlayer.toggelInJail());
	}

	@Test
	public void testIsActive() {
		//beforeTest();
		assertTrue(freshPlayer.isActive());
		assertTrue(oldPlayer.isActive());
	}

	@Test
	public void testSetActive() {
		//beforeTest();
		freshPlayer.setActive(false);
		oldPlayer.setActive(false);
		assertFalse(freshPlayer.isActive());
		assertFalse(oldPlayer.isActive());
		freshPlayer.setActive(true);
		assertTrue(freshPlayer.isActive());
		assertFalse(oldPlayer.isActive());
		freshPlayer.setActive(false);
		oldPlayer.setActive(true);
		assertFalse(freshPlayer.isActive());
		assertTrue(oldPlayer.isActive());
	}

	@Test
	public void testToggelActive() {
		//beforeTest();
		assertFalse(freshPlayer.toggelActive());
		assertFalse(oldPlayer.toggelActive());
		assertTrue(freshPlayer.toggelActive());
		assertTrue(oldPlayer.toggelActive());
	}

	@Test
	public void testIsTurn() {
		//beforeTest();
		assertFalse(freshPlayer.isTurn());
		assertFalse(oldPlayer.isTurn());
	}

	@Test
	public void testSetTurn() {
		//beforeTest();
		freshPlayer.setTurn(true);
		assertTrue(freshPlayer.isTurn());
		assertFalse(oldPlayer.isTurn());
		freshPlayer.setTurn(false);
		oldPlayer.setTurn(true);
		assertFalse(freshPlayer.isTurn());
		assertTrue(oldPlayer.isTurn());
	}

	@Test
	public void testToggleTurn() {
		//beforeTest();
		assertTrue(freshPlayer.toggleTurn());
		assertTrue(oldPlayer.toggleTurn());
		assertFalse(freshPlayer.toggleTurn());
		assertFalse(oldPlayer.toggleTurn());
	}

	@Test
	public void testGetJailCount() {
		//beforeTest();
		assertEquals(freshPlayer.getJailCount(), 0);
		assertEquals(oldPlayer.getJailCount(), 1);
	}

	@Test
	public void testSpendANightInJail() {
		//beforeTest();
		assertEquals(freshPlayer.spendANightInJail(), 1);
		assertEquals(oldPlayer.spendANightInJail(), 2);
	}

	@Test
	public void testResetJailCount() {
		//beforeTest();
		freshPlayer.resetJailCount();
		oldPlayer.resetJailCount();
		assertEquals(freshPlayer.getJailCount(), 0);
		assertEquals(oldPlayer.getJailCount(), 0);
	}

	@Test
	public void testPlayerOwns() {
		//beforeTest();
		assertFalse(freshPlayer.playerOwns(junk));
		assertFalse(freshPlayer.playerOwns(metra));
		assertFalse(freshPlayer.playerOwns(comcast));
		assertTrue(oldPlayer.playerOwns(junk));
		assertTrue(oldPlayer.playerOwns(metra));
		assertTrue(oldPlayer.playerOwns(comcast));
	}

	@Test
	public void testAddProperty() {
		//beforeTest();
		Property crap = new Colored("Crap Dr.", 40, 2,false, rents, 3);
		freshPlayer.addProperty(crap);
		assertTrue(freshPlayer.playerOwns(crap));
		assertEquals(crap.getOwner(), freshPlayer);
		oldPlayer.addProperty(crap);
		assertTrue(oldPlayer.playerOwns(crap));
		assertEquals(crap.getOwner(), oldPlayer);
		freshPlayer.removeProperty(crap);
		oldPlayer.removeProperty(crap);
	}

	@Test
	public void testRemovePropertyProperty() {
		//beforeTest();
		Property crap = new Colored("Crap Dr.", 40, 2,false, rents, 3);
		freshPlayer.addProperty(crap);
		assertTrue(freshPlayer.playerOwns(crap));
		assertEquals(crap.getOwner(), freshPlayer);
		freshPlayer.removeProperty(crap);
		assertFalse(freshPlayer.playerOwns(crap));
		assertNull(crap.getOwner());
		
		oldPlayer.addProperty(junk);
		assertTrue(oldPlayer.playerOwns(junk));
		assertEquals(junk.getOwner(), oldPlayer);
		
		oldPlayer.removeProperty(junk);
		assertFalse(oldPlayer.playerOwns(junk));
		assertNull(junk.getOwner());
	}
	
	@Test
	public void testRemovePropertyString(){
		//beforeTest();
		
		Property crap = new Colored("Crap Dr.", 40, 2,false, rents, 3);
		freshPlayer.addProperty(crap);
		assertTrue(freshPlayer.playerOwns(crap));
		assertEquals(crap.getOwner(), freshPlayer);
		
		freshPlayer.removeProperty(crap.getName());
		assertFalse(freshPlayer.playerOwns(crap));
		assertNull(crap.getOwner());
	}

}
