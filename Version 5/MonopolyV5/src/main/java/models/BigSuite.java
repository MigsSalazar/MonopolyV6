package main.java.models;

public class BigSuite implements Suite {

	private final String COLOR;
	private final Colored FIRST;
	private final Colored SECOND;
	private final Colored THIRD;
	
	public BigSuite(String c, Colored f, Colored s, Colored t){
		COLOR = c;
		FIRST = f;
		SECOND = s;
		THIRD = t;
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
		max = THIRD.getGrade()>max ? THIRD.getGrade() : max;
		return max;
	}

	@Override
	public int smallestGrade() {
		// TODO Auto-generated method stub
		int min = 10;
		min = FIRST.getGrade()<min ? FIRST.getGrade() : min;
		min = SECOND.getGrade()<min ? SECOND.getGrade() : min;
		min = THIRD.getGrade()<min ? THIRD.getGrade() : min;
		return min;
	}

	@Override
	public boolean hasMortgage() {
		// TODO Auto-generated method stub
		return FIRST.isMortgaged() || SECOND.isMortgaged() || THIRD.isMortgaged();
	}

	@Override
	public int gradeDisparity() {
		// TODO Auto-generated method stub
		return largestGrade() - smallestGrade();
	}

}
