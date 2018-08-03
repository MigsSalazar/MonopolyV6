package edu.illinois.masalzr2.templates;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.google.gson.annotations.Expose;

import edu.illinois.masalzr2.gui.Board;
import edu.illinois.masalzr2.gui.FrameMenu;
import edu.illinois.masalzr2.gui.Notices;
import edu.illinois.masalzr2.gui.Scoreboard;
import edu.illinois.masalzr2.gui.Stamp;
import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.masters.LogMate;
import edu.illinois.masalzr2.masters.LogMate.Logger;
import edu.illinois.masalzr2.models.Counter;
import edu.illinois.masalzr2.models.Dice;
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
	@Expose private String texture;
	
	/*
	
	@Expose private Map<String, Player> players;
	@Expose private Map<String, Property> properties;
	@Expose private Map<String, Suite> suites;
	@Expose private PositionIndex propertyPositions;
	@Expose private ArrayList<GameCard> chance;
	@Expose private ArrayList<GameCard> commchest;
	@Expose private int[][] paintByNumbers;
	@Expose private String[] icons;
	@Expose private Stamp[][] stampCollection;
	@Expose private Map<String, GameToken> playerTokens;
	@Expose private int[][] stickerBook;
	@Expose private String[] stickers;
	@Expose private String currency;
	@Expose private String texture;
	 */
	
	public TemplateJson(GameVariables gv) {
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
		texture				= gv.getTexture();
		
		
	}
	
}
