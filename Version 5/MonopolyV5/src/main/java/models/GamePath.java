package main.java.models;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;


public class GamePath {

	
	@Expose private ArrayList<Pair<Integer,Integer>> steps;
	@Expose private int currentStep = 0;
	@Expose private boolean locked = false;
	
	public GamePath(){
		steps = new ArrayList<Pair<Integer,Integer>>();
	}
	
	public GamePath(ArrayList<Pair<Integer,Integer>> s){
		steps = s;
	}
	
	public GamePath(ArrayList<Pair<Integer,Integer>> s, int cs){
		steps = s;
		currentStep = cs;
	}
	
	public Pair<Integer,Integer> forward(){
		if( !locked ){
			currentStep = (currentStep+1)%steps.size();
		}
		return steps.get(currentStep);
	}
	
	public Pair<Integer,Integer> getStepAt(int index){
		return steps.get(index);
	}
	
	public int getCurrentStep(){
		return currentStep;
	}
	
	public int move(int num){
		if( !locked ){
			currentStep = (currentStep+num)%steps.size();
		}
		return currentStep;
	}
	
	public int setCurrentStep(int jump){
		if( !locked ){
			if(jump < steps.size() && jump >= 0){
				currentStep = jump;
			}else{
				currentStep = jump%steps.size();
			}
		}
		return currentStep;
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
	
	public void addStep(Pair<Integer,Integer> cp){
		steps.add(cp);
	}
	
	public int getCurrentRow(){
		return steps.get(currentStep).first;
	}
	
	public int getCurrentCol(){
		return steps.get(currentStep).second;
	}
	
}
