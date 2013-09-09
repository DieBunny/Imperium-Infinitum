package nich.Classes;

public class Resources {
	public int Fuel;
	public int Research;
	public int BattleShip;
	public int Destroyer;
	public int Frigate;

	
	
	
	//initial resources for player - 0,0,0,0,0
	//permanent resources for planet - 0,1,0,0,1
	public Resources (int fuel, int research, int battleship, int destroyer, int frigate)
	{
		this.Fuel =fuel;
		this.Research = research;
		this.BattleShip = battleship;
		this.Destroyer = destroyer;
		this.Frigate = frigate;
	}
}
