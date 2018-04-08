package edu.illinois.masalzr2.models;

public class Counter{
	
	private int count;
	private int max;
	private int min;
	
	public Counter(){
		setCount(0);
		max = 0;
		min = 0;
	}
	
	public Counter(int c){
		setCount(c);
		max = 0;
		min = 0;
	}
	
	public Counter(int c, int max, int min){
		count = c;
		this.max = max;
		this.min = min;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	public int add(int a){
		count += a;
		if(min < max && count > max){
			count = max;
		}
		return count;
	}
	
	public int sub(int s){
		count -= s;
		if(min < max && count < min){
			count = min;
		}
		return count;
	}
	
}
