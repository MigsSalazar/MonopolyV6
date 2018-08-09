package edu.illinois.masalzr2.models;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

public class Counter implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter @Expose
	private int count;
	@Getter @Setter @Expose
	private int max;
	@Getter @Setter @Expose 
	private int min;
	
	public Counter(){
		max = 0;
		min = 0;
		setCount(0);
	}
	
	public Counter(int c){
		max = 0;
		min = 0;
		setCount(c);
	}
	
	public Counter(int c, int max, int min){
		this.max = max;
		this.min = min;
		setCount(c);
	}
	
	public void setCount(int c) {
		count = c;
		if( max > min) {
			count %= max;
		}
	}
	
	public int add(int a){
		count += a;
		count = count % max;
		return count;
	}
	
	public int sub(int s){
		count -= s;
		if(count < min) {
			count = min;
		}
		return count;
	}
	
}
