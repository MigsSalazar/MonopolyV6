package main.java.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class SmallSuite implements Suite {
	
	@Expose private final String COLOR;
	@Expose private final Colored FIRST;
	@Expose private final Colored SECOND;
	
	public SmallSuite(String c, Colored f, Colored s){
		COLOR = c;
		FIRST = f;
		SECOND = s;
		//setPropertySuite();
	}
	
	@Override
	public String getColor() {
		// TODO Auto-generated method stub
		return COLOR;
	}

	@Override
	public boolean playerHasMonopoly(Player pl) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public int largestGrade() {
		// TODO Auto-generated method stub
		int max = 0;
		max = FIRST.getGrade()>max ? FIRST.getGrade() : max;
		max = SECOND.getGrade()>max ? SECOND.getGrade() : max;
		return max;
	}

	@Override
	public int smallestGrade() {
		// TODO Auto-generated method stub
		int min = 10;
		min = FIRST.getGrade()<min ? FIRST.getGrade() : min;
		min = SECOND.getGrade()<min ? SECOND.getGrade() : min;
		return min;
	}

	@Override
	public boolean hasMortgage() {
		// TODO Auto-generated method stub
		return FIRST.isMortgaged() || SECOND.isMortgaged();
	}

	@Override
	public int gradeDisparity() {
		// TODO Auto-generated method stub
		return largestGrade() - smallestGrade();
	}
	
	public void setPropertySuite(){
		FIRST.setSuite(this);
		SECOND.setSuite(this);
	}
	
	public List<Property> getProperties(){
		ArrayList<Property> props = new ArrayList<Property>();
		props.add(FIRST);
		props.add(SECOND);
		return props;
	}

}
