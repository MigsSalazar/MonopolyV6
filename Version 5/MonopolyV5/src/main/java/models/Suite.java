package main.java.models;

public interface Suite {
	
	/**
	 * Returns the title of the suite as a String object
	 * @return	String of the title: a color name
	 */
	public abstract String getColor();

	/**
	 * Runs through a player's properties to see if they hold the complete suite stored in this object
	 * @param pl	Player to analyze
	 * @return		a boolean confirming or denying the player's claim to hold a monopoly
	 */
	public abstract boolean playerHasMonopoly(Player pl);

	/**
	 * Searches through the suite to determine the highest property value in the suite
	 * @return	integer of the largest property value found
	 */
	public abstract int largestGrade();

	/**
	 * Searches through the suite to determine the lowest property value in the suite
	 * @return	integer of the smallest property value found
	 */
	public abstract int smallestGrade();

	/**
	 * Checks to see if any properties contained in the suite have been mortgaged
	 * @return	true if mortgaged, false if not
	 */
	public abstract boolean hasMortgage();

	/**
	 * Determines the range of the property value.
	 * Range should never be greater than 1 or less than -1
	 * @return	integer value of the disparity
	 */
	public abstract int gradeDisparity();

}