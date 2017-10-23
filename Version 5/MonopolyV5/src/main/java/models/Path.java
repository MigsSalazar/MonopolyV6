package main.java.models;

import java.util.ArrayList;

public class Path {

	
	private ArrayList<PathStep> steps;
	private int currentStep = 0;
	private boolean goBack = false;
	private boolean locked = false;
	
	public Path(){
		steps = new ArrayList<PathStep>();
	}
	
	public Path(PathStep firstStep){
		steps = new ArrayList<PathStep>();
		steps.add(firstStep);
	}
	
	public Path(boolean back){
		steps = new ArrayList<PathStep>();
		goBack = back;
	}
	
	public Path(PathStep firstStep, boolean back){
		steps = new ArrayList<PathStep>();
		steps.add(firstStep);
		goBack = back;
	}
	
	public PathStep getStep(int index){
		return steps.get(index);
	}
	
	public PathStep move(int num){
		if( !locked ){
			if(num < 0 && goBack){
				currentStep = (currentStep + num)%steps.size();
				if( currentStep < 0){
					currentStep += steps.size();
				}
			}else if(num >=0){
				currentStep = (currentStep+num)%steps.size();
			}
		}
		return steps.get(currentStep);
	}
	
	public PathStep setStep(int jump){
		if( !locked ){
			if(jump < steps.size() && jump >= 0){
				currentStep = jump;
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
	
}
