package edu.illinois.masalzr2.notices;

public class ListEvent {
	
	private Object source;
	
	public ListEvent(Object s){
		if (s == null)
			throw new IllegalArgumentException("null source");
		source = s;
	}
	
	public Object getSource(){
		return source;
	}
	
	@Override
	public String toString(){
		return getClass().getName() + "[source:" + source + "]";
	}
}
