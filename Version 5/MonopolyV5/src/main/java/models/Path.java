package main.java.models;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;


public class Path {

	
	@Expose private ArrayList<CoordPair> steps;
	@Expose private int currentStep = 0;
	@Expose private boolean locked = false;
	
	public Path(){
		steps = new ArrayList<CoordPair>();
	}
	
	public Path(ArrayList<CoordPair> s){
		steps = s;
	}
	
	public Path(ArrayList<CoordPair> s, int cs){
		steps = s;
		currentStep = cs;
	}
	
	public CoordPair getCurrentStep(int index){
		return steps.get(index);
	}
	
	public CoordPair move(int num){
		if( !locked ){
			currentStep = (currentStep+num)%steps.size();
		}
		return steps.get(currentStep);
	}
	
	public CoordPair setCurrentStep(int jump){
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
	
	public void addStep(CoordPair cp){
		steps.add(cp);
	}
	
	public int getCurrentRow(){
		return steps.get(currentStep).getRow();
	}
	
	public int getCurrentCol(){
		return steps.get(currentStep).getCol();
	}
	
}
