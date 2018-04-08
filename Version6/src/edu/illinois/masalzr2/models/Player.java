package edu.illinois.masalzr2.models;

import com.google.gson.annotations.Expose;

public class Player {
	
	@Expose private String name;
	
	public String getName(){
		return name;
	}
}
