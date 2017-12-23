package main.java.models;

/**
 * Model class for basic assets when trading
 * in Monopoly
 * @author Miguel Salazar
 *
 */
public class Asset {
	private String asset;
	private String type;
	private int worth;
	
	/**
	 * Default constructor. Must contain the name, type,
	 * and cash value of the asset. Cash assets are written
	 * as "$$$",type,$$$
	 * @param a	String name of the asset
	 * @param t	String type of the asset. Three possible types:
	 * 			1-cash
	 * 			2-gojf
	 * 			3-prop
	 * @param w	int value denoting cash value of asset
	 */
	public Asset(String a, String t, int w){
		asset = a;
		type = t;
		worth = w;
	}
	
	/**
	 * Returns String name of the asset
	 * @return	String asset's name
	 */
	public String getAsset(){
		return asset;
	}
	
	/**
	 * Returns the type of asset contained
	 * Three possible types:
	 * 1-cash
	 * 2-gojf (Get out of Jail Free card)
	 * 3-prop (any and all properties)
	 * @return	String denoting type
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * Returns an int denoting the value of the stored asset
	 * @return
	 */
	public int getWorth(){
		return worth;
	}
	
	public String toString(){
		return type+": "+asset+" worth "+worth;
	}
	
}
