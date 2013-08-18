package nich.Classes;

public class Fleet {

	public int battleships;
	public int destroyers;
	public int frigates;
		
	private int size = battleships+destroyers+frigates;
	
	public int getSize() {return size;}
//	public void setSize() {size = battleships+destroyers+frigates;}
	
	public Player owner;
	public Planet location;
	
	
	
}
