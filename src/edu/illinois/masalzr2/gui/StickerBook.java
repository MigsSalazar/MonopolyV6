package edu.illinois.masalzr2.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Hashtable;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class StickerBook {
	
	private List<String> stickers;
	private Map<Integer, Map<Integer, Map<Integer, Integer>>> pages;
	private List<ImageIcon> coloredIn;
	private boolean dirtyColors = false;
	
	private int width;
	private int height;

	
	public StickerBook(int w, int h) {
		width = w;
		height = h;
		makePages(width, height);
		stickers = new ArrayList<String>();
		coloredIn = new ArrayList<ImageIcon>();
	}
	
	public StickerBook(int w, int h, ArrayList<String> st) {
		width = w;
		height = h;
		makePages(width,height);
		
		stickers = st;
		
		coloredIn = new ArrayList<ImageIcon>();
		
		for(String s : stickers){
			coloredIn.add(new ImageIcon(s));
		}
		
	}
	
	public StickerBook(ArrayList<String> st, Map<Integer, Map<Integer, Map<Integer, Integer>>> pa){
		stickers = st;
		pages = pa;
		width = pages.size();
		int finder = 0;
		while( !pages.containsKey(finder) ){
			finder++;
		}
		height = pages.get(finder).size();
		coloredIn = new ArrayList<ImageIcon>();
		
		refreshColoredIn();
	}
	
	public void placeSticker(int x, int y, int index){
		if(index < 0 || index >= stickers.size())
			return;
		
		Map<Integer, Integer> current = pages.get(x).get(y);
		current.put(current.size(), index);
	}
	
	public void addSticker(String location){
		if(!stickers.contains(location)){
			stickers.add(location);
			dirtyColors = true;
		}
	}
	
	public void removeSticker(String name){
		if(stickers.remove(name)){
			dirtyColors = true;
		}
	}
	
	public void removeSticker(int index){
		if(index < stickers.size()){
			stickers.remove(index);
			dirtyColors = true;
		}
	}
	
	public void placeStickerAtPage(int x, int y, int index, int pageNum){
		if(index < 0 || index >= stickers.size())
			return;
		pages.get(x).get(y).put(pageNum, index);
	}
	
	public int pageDepthAt(int x, int y){
		return pages.get(x).get(y).size();
	}
	
	public List<String> getStickerFileLocation(){
		return stickers;
	}
	
	public List<String> listStickerLocationsAt(int x, int y){
		List<String> locs = new ArrayList<String>();
		for(int s : pages.get(x).get(y).values()){
			locs.add( stickers.get(s) );
		}
		
		return locs;
	}
	
	public ImageIcon getColoredStickerAt(int index){
		return coloredIn.get(index);
	}
	
	public List<ImageIcon> getPaintedIcons(){
		refreshColoredIn();
		return coloredIn.subList(0, coloredIn.size());
	}
	
	public List<Integer> listStickerIndicesAt(int x, int y){
		return new ArrayList<Integer>(pages.get(x).get(y).values());
	}
	
	public int getIconIndex(ImageIcon icon){
		return getIconIndex(icon.getDescription());
	}
	
	public int getIconIndex(String location){
		return stickers.indexOf(location);
	}
	
	private void refreshColoredIn(){
		if(!dirtyColors){
			return;
		}
		String homeDir = System.getProperty("user.dir");
		int i;
		for(i=0; i<stickers.size(); i++){
			if( i >= coloredIn.size() ){
				coloredIn.add(new ImageIcon( homeDir + stickers.get(i)) );
			}else{
				ImageIcon sticker = new ImageIcon( homeDir + stickers.get(i) );
				if( !coloredIn.get(i).equals( sticker ) ){
					coloredIn.set(i, sticker);
				}
			}
		}
		if(i < coloredIn.size()){
			for( int j = coloredIn.size()-1; j >= i; j--){
				coloredIn.remove(j);
			}
		}
		dirtyColors = false;
	}
	
	private void makePages(int w, int h) {
		pages = new Hashtable<Integer, Map<Integer, Map<Integer, Integer>>>(w, 1.0f);
		for(int x = 0; x < w; x++){
			Map<Integer, Map<Integer, Integer>> current = new Hashtable<Integer, Map<Integer, Integer>>(h, 1.0f);
			pages.put(x, current);
			for(int y = 0; y<h; y++){
				Map<Integer, Integer> square = new HashMap<Integer, Integer>();
				current.put(y, square);
			}
		}
	}
	
}
