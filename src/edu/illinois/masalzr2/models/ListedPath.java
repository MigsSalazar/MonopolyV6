package edu.illinois.masalzr2.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

public class ListedPath implements Serializable, Router{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Expose @Getter @Setter
	private List<int[]> coords;
	/*
	@Expose 
	private int[] specialX;
	@Expose 
	private int[] specialY;
	*/
	@Getter @Setter @Expose 
	private int step;
	/*
	@Getter @Setter @Expose 
	private boolean locked;
	*/
	public ListedPath(){
		coords = new ArrayList<int[]>();
		
		coords.add(new int[]{0,0});
		
		step = 0;
	}
	
	public ListedPath(int[] x, int[] y){
		coords = new ArrayList<int[]>();
		for(int i=0; i<x.length && i<y.length; i++){
			coords.add(new int[]{x[i],y[i]});
		}
		
		step = 0;
	}
	
	public ListedPath(List<int[]> c){
		coords = c;
		step = 0;
	}
	
	public ListedPath(int[] x, int[] y, int s){
		for(int i=0; i<x.length && i<y.length; i++){
			coords.add(new int[]{x[i],y[i]});
		}
		
		step = s;
	}
	
	public ListedPath(List<int[]> c, int s){
		coords = c;
		step = s;
	}
	
	
	/*
	public int[] moveOne(){
		step++;
		step %= (x.length<y.length?x.length:y.length);
		return getCoords();
	}
	
	public int[] move(int m){
		step = (step + m) % x.length;
		return getCoords();
	}
	
	public int[] getCoords(){
		int[] retval = new int[2];
		retval[0] = x[step];
		retval[1] = y[step];
		return retval;
	}
	public int stepCount() {
		return (x.length < y.length) ? x.length : y.length;
	}
	public int[] getCoordsAtStep(int s){
		int[] retval = {-1,-1};
		if(s < x.length && s < y.length){
			retval[0] = x[s];
			retval[1] = y[s];
		}
		return retval;
	}
	
	public int[] getSpecialCase(int sp){
		int[] retval = {-1,-1};
		if(sp < specialX.length && sp < specialY.length){
			retval[0] = specialX[sp];
			retval[1] = specialY[sp];
		}
		return retval;
	}
	*/
	
	public void moveStep(int m){
		step = (m + step)%coords.size();
	}
	
	public int[] currentCoords(){
		return coords.get(step);
	}

	public List<int[]> findPathTo(int end){
		return findPath(step, end);
	}
	
	public List<int[]> findFullPath(){
		return findPath(0, coords.size());
	}
	
	@Override
	public List<int[]> findPath(int c1, int c2) {
		
		if(c1 > -1 && c2 <= coords.size()){
			return coords.subList(c1, c2);
		}
		
		List<int[]> ret = new ArrayList<int[]>();
		
		for(int i = c1; i<c2; i++){
			int[] copied = coords.get(i%coords.size());
			ret.add( new int[]{ copied[0], copied[1] } );
		}
		
		return ret;
		
	}
	
}
