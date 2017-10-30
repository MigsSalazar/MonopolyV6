package main.java.models;

import java.util.ArrayList;

public class Path {

	
	private ArrayList<PathStep> steps;
	private int currentStep = 0;
	private boolean locked = false;
	private Piece pathWalker;
	
	public Path(ArrayList<PathStep> s, Piece pw){
		steps = s;
		pathWalker = pw;
	}

	
	public PathStep getStep(int index){
		return steps.get(index);
	}
	
	public PathStep move(int num){
		if( !locked ){
			currentStep = (currentStep+num)%steps.size();
		}
		return steps.get(currentStep);
	}
	
	public PathStep setStep(int jump){
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
	
	public Piece getPiece(){
		return pathWalker;
	}
	
	public int getCurrentRow(){
		return steps.get(currentStep).getRow();
	}
	
	public int getCurrentCol(){
		return steps.get(currentStep).getCol();
	}
	
}
