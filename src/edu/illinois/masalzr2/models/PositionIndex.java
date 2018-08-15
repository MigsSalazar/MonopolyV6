package edu.illinois.masalzr2.models;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

public class PositionIndex implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Expose 
	private int[] x;
	@Expose 
	private int[] y;
	
	@Expose 
	private int[] specialX;
	@Expose 
	private int[] specialY;
	
	@Getter @Setter @Expose 
	private int step;
	
	@Getter @Setter @Expose 
	private boolean locked;
	
	public PositionIndex(){
		x = new int[1];
		
		y = new int[1];
		x[0] = y[0] = 0;
		
		specialX = new int[1];
		specialY = new int[1];
		
		specialX[0] = specialY[0] = 0;
		
		step = 0;
		
		locked = true;
	}
	
	public PositionIndex(int[] x, int[] y){
		this.x = x;
		this.y = y;
		
		specialX = new int[1];
		specialY = new int[1];
		
		specialX[0] = specialY[0] = 1;
		
		step = 0;
		
		locked = false;
	}
	
	public PositionIndex(int[] x, int[] y, int[] spx, int[] spy){
		this.x = x;
		this.y = y;
		
		specialX = spx;
		specialY = spy;
		
		step = 0;
		
		locked = false;
	}
	
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
	
}
