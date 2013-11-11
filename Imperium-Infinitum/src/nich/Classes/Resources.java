package nich.Classes;

public class Resources {
	public String type;
	public int value;
	public Planet linkedPlanet;
	
	public Resources (String stype, int pvalue, Planet lplanet )
	{
		type = stype;
		value=pvalue;
		linkedPlanet = lplanet;
	}
}
