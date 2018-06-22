package edu.illinois.masalzr2.templates;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import com.google.gson.annotations.Expose;

import edu.illinois.masalzr2.gui.Stamp;
import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.models.GameCard;
import edu.illinois.masalzr2.models.GameToken;
import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.models.PositionIndex;
import edu.illinois.masalzr2.models.Property;
import edu.illinois.masalzr2.models.Suite;

public class TemplateJson {
	
	@Expose private HashMap<String, Player> players;
	@Expose private HashMap<String, Property> properties;
	@Expose private HashMap<String, Suite> suites;
	@Expose private PositionIndex propertyPositions;
	@Expose private ArrayList<GameCard> chance;
	@Expose private ArrayList<GameCard> commchest;
	
	@Expose private int[][] paintByNumbers;
	@Expose private String[] icons;
	@Expose private Stamp[][] stampCollection;
	@Expose private HashMap<String, GameToken> playerTokens;
	@Expose private int[][] stickerBook;
	@Expose private String[] stickers;
	
	@Expose private String currency;
	@Expose private String name;
	@Expose private ImageIcon icon;
	
	/*
	 * variables.put("currency", currency);
		variables.put("housecount", houseCount);
		variables.put("hotelcount", hotelCount);
		variables.put("frame", frame);
		variables.put("proppos", propertyPositions);
		variables.put("texture", texture);
		variables.put("chance", chance);
		variables.put("comchest", commchest);
		variables.put("dice", gameDice);
		variables.put("railcount", railCount);
		variables.put("utilcount", utilCount);
		variables.put("paintbynumbers", paintByNumbers);
		variables.put("playertokens", playerTokens);
		variables.put("icons", icons);
		variables.put("stampcollection", stampCollection);
		variables.put("stickerbook", stickerBook);
		variables.put("stickers", stickers);
		variables.put("properties", properties);
		variables.put("players", players);
		variables.put("suites", suites);
		variables.put("savefile", saveFile);
	 */
	
	
	@SuppressWarnings("unchecked")
	public TemplateJson(GameVariables gv) {
		
		String sep = File.separator;
		
		players 			= (HashMap<String,Player>)		gv.getVariable("players");
		properties 			= (HashMap<String,Property>)	gv.getVariable("properties");
		suites 				= (HashMap<String,Suite>)		gv.getVariable("suites");
		propertyPositions 	= (PositionIndex)				gv.getVariable("proppos");
		chance 				= (ArrayList<GameCard>)			gv.getVariable("chance");
		commchest 			= (ArrayList<GameCard>)			gv.getVariable("commchest");
		paintByNumbers 		= (int[][])						gv.getVariable("paintbynumbers");
		icons 				= (String[]) 					gv.getVariable("icons");
		stampCollection 	= (Stamp[][]) 					gv.getVariable("stampcollection");
		playerTokens 		= (HashMap<String,GameToken>)	gv.getVariable("playertokens");
		stickerBook 		= (int[][]) 					gv.getVariable("stickerbook");
		stickers 			= (String[]) 					gv.getVariable("stickers");
		currency 			= (String) 						gv.getVariable("currency");
		name = "default";
		
		icon = new ImageIcon(System.getProperty("user.dir")+sep+"textures"+sep+"default"+sep);
		
	}
	
}
