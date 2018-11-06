package edu.illinois.masalzr2.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CyclicalPathModel implements ListedPathModel, Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<int[]> generatePath(int c1, int c2, ListedPath path) {
		if(path == null){
			return new ArrayList<int[]>();
		}
		List<int[]> coords = path.getCoords();
		List<int[]> ret = new ArrayList<int[]>();
		
		if(c2 < c1){
			int temp = c2;
			c2 = c1;
			c1 = temp;
		}
		
		int start;
		int end;
		
		if(c1 < 0){
			start = coords.size() + (c1 % coords.size());
			end = (c2 - c1) + start;
		}else{
			start = c1;
			end = c2;
		}
		
		for(int i = start; i<end; i++){
			int[] copied = coords.get(i%coords.size());
			ret.add( new int[]{ copied[0], copied[1] } );
		}
		
		return ret;
		
	}

}
