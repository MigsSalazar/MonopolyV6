package edu.illinois.masalzr2.notices;


public interface ListListener {

	public void pushMe(ListEvent le);
	
	public void popMe(ListEvent le);
	
	public void pullMe(ListEvent le);
}
