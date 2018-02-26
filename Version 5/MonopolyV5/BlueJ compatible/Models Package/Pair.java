 

import com.google.gson.annotations.Expose;

public class Pair<K, V> {
	
	@Expose public K first;
	@Expose public V second;
	
	public Pair(K f, V s){
		first = f;
		second = s;
	}
}
