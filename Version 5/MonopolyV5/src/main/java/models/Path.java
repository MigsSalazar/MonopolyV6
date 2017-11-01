package main.java.models;

import java.util.ArrayList;


public class Path {

	
	private ArrayList<CoordPair> steps;
	private int currentStep = 0;
	private boolean locked = false;
	
	public Path(ArrayList<CoordPair> s){
		steps = s;
	}
	
	public CoordPair getStep(int index){
		return steps.get(index);
	}
	
	public CoordPair move(int num){
		if( !locked ){
			currentStep = (currentStep+num)%steps.size();
		}
		return steps.get(currentStep);
	}
	
	public CoordPair setStep(int jump){
		if( !locked ){
			if(jump < steps.size() && jump >= 0){
				currentStep = jump;
			}else{
				currentStep = jump%steps.size();
			}
		}
		return steps.get(currentStep);
	}
	
	public boolean isLocked(){
		return locked;
	}
	
	public void setLocked(boolean l){
		locked = l;
	}
	
	public boolean toggleLocked(){
		locked = !locked;
		return locked;
	}
	
	
	public int getCurrentRow(){
		return steps.get(currentStep).getRow();
	}
	
	public int getCurrentCol(){
		return steps.get(currentStep).getCol();
	}
	
}
