package main.java.action;

import main.java.models.Player;
import main.java.models.Property;

public class BankPropertyActions {
	
	
	public static void sellUnownedProperty(Player play, Property prop){
		play.addProperty(prop);
		play.subCash(prop.getPrice());
	}
	
	public static void sellUnownedProperty(Player play, Property prop, int price){
		play.addProperty(prop);
		play.subCash(price);
	}
	
	public static void rentOwnedProperty(Player play, Property prop){
		play.subCash(prop.getRent());
	}
}
