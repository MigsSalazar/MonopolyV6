package edu.illinois.masalzr2.models;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

public class MonopolizedToken extends GameToken {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter @Expose
	private boolean locked = false;

	@Getter @Setter @Expose
	private ListedPath jailCell;
	
	public MonopolizedToken(int t, String dir, ListedPath p, ListedPath j) {
		super(t, dir, p);
		jailCell = j;
		p.setListedPathModel(new CyclicalPathModel());
		j.setListedPathModel(new CyclicalPathModel());
	}
	
	@Override
	public ListedPath getRouting(){
		if(locked){
			return jailCell;
		}
		return (ListedPath)routing;
	}

}
