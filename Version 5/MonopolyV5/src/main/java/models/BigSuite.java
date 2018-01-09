package main.java.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class BigSuite implements Suite {

	@Expose public final String COLOR;
	@Expose public final Colored FIRST;
	@Expose public final Colored SECOND;
	@Expose public final Colored THIRD;
	
	public BigSuite(String c, Colored f, Colored s, Colored t){
		COLOR = c;
		FIRST = f;
		SECOND = s;
		THIRD = t;
		//setPropertySuite();
	}
	
	@Override
	public String getColor() {
		return COLOR;
	}

	@Override
	public boolean playerHasMonopoly(Player pl) {
		String name = pl.getName();
		return FIRST.getOwner().equals(name) &&
				SECOND.getOwner().equals(name) &&
				THIRD.getOwner().equals(name);
	}

	@Override
	public int largestGrade() {
		int max = 0;
		max = FIRST.getGrade()>max ? FIRST.getGrade() : max;
		max = SECOND.getGrade()>max ? SECOND.getGrade() : max;
		max = THIRD.getGrade()>max ? THIRD.getGrade() : max;
		return max;
	}

	@Override
	public int smallestGrade() {
		int min = 10;
		min = FIRST.getGrade()<min ? FIRST.getGrade() : min;
		min = SECOND.getGrade()<min ? SECOND.getGrade() : min;
		min = THIRD.getGrade()<min ? THIRD.getGrade() : min;
		return min;
	}

	@Override
	public boolean hasMortgage() {
		return FIRST.isMortgaged() || SECOND.isMortgaged() || THIRD.isMortgaged();
	}

	@Override
	public int gradeDisparity() {
		return largestGrade() - smallestGrade();
	}
	
	public void setPropertySuite(){
		FIRST.setSuite(this);
		SECOND.setSuite(this);
		THIRD.setSuite(this);
	}
	
	public List<Colored> getProperties(){
		ArrayList<Colored> props = new ArrayList<Colored>();
		props.add(FIRST);
		props.add(SECOND);
		props.add(THIRD);
		return props;
	}

	@Override
	public int getCumulativeGrade() {
		return FIRST.getGrade()+SECOND.getGrade()+THIRD.getGrade();
	}
	
}
