package nich.Classes;

import java.util.List;


public class Player {
	
	//woob woob test
	public String name;
	//public Resources resources;
	public int fuel;
	public int research;
	public int victoryPoints;
	
	//maybe this is not necessary? we have fleet for this (with owner,location)
	public List<BattleShip> battleshiplist;
	public List<Destroyer> destroyerlist;
	public List<Frigatte> frigattelist;

}
