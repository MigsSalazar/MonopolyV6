package edu.illinois.masalzr2.templates;

import java.io.File;
import java.util.List;
import java.util.Map;

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
	
	@Expose private Map<String, Player> players;
	@Expose private Map<String, Property> properties;
	@Expose private Map<String, Suite> suites;
	@Expose private PositionIndex propertyPositions;
	@Expose private List<GameCard> chance;
	@Expose private List<GameCard> commchest;
	
	@Expose private int[][] paintByNumbers;
	@Expose private String[] icons;
	@Expose private Stamp[][] stampCollection;
	@Expose private Map<String, GameToken> playerTokens;
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
	
	public TemplateJson(GameVariables gv) {
		
		String sep = File.separator;
		
		players 			= gv.getPlayers();
		properties 			= gv.getProperties();
		suites 				= gv.getSuites();
		propertyPositions 	= gv.getPropertyPositions();
		chance 				= gv.getChance();
		commchest 			= gv.getCommchest();
		paintByNumbers 		= gv.getPaintByNumbers();
		icons 				= gv.getIcons();
		stampCollection 	= gv.getStampCollection();
		playerTokens 		= gv.getPlayerTokens();
		stickerBook 		= gv.getStickerBook();
		stickers 			= gv.getStickers();
		currency 			= gv.getCurrency();
		name = "default";
		
		icon = new ImageIcon(System.getProperty("user.dir")+sep+"textures"+sep+"default"+sep);
		
	}
	
}
