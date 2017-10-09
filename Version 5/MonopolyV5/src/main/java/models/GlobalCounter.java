package main.java.models;

public class GlobalCounter {

	
	private int count;
	private int max;
	
	/**
	 * GlobalCounter stores a counter that can be used across objects or on a global scope.
	 * GlobalCounter will never reach bellow 0 but has no maximum
	 */
	public GlobalCounter(){
		count = 0;
		max = -1;
	}
	
	/**
	 * Increases counter by 1
	 * @return	current count after increase
	 */
	public int incCout(){
		count++;
		if(max > -1){
			if(count > max){
				count = max;
			}
		}
		return count;
	}
	
	/**
	 * Increases count by n
	 * @param n	integer value to increase count by
	 * @return	current count after n has been added
	 */
	public int incCount(int n){
		
		count += n;
		if(max > -1){
			if(count > max){
				count = max;
			}
		}
		return count;
	}
	
	/**
	 * Decreases counter by 1. Does not go below 0
	 * @return	current count after decrease
	 */
	public int decCount(){
		if(count>0) count--;
		return count;
	}
	
	/**
	 * Decreases counter by n. Sets count to 0 if below
	 * @param n	integer value to decrease count by
	 * @return	current count after decrease
	 */
	public int decCounter(int n){
		count -= n;
		if(count < 0) count = 0;
		return count;
	}
	
	/**
	 * 
	 * @return	current count
	 */
	public int getCount(){
		return count;
	}
	
	public int getMax(){
		return max;
	}
	
	public void setMax(int m){
		max = m;
	}
	
}
