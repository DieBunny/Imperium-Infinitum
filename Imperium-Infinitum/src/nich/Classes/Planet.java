package nich.Classes;

import java.util.List;

public class Planet {
public List<Resources> planetRes;
public CombatPhases combatPhases;
public int xPos;
public int yPos;
public String planetType; //This is type used for visuals. There are 3 types in total. Fuel/Res Ship/Res Fuel/Ship. Planets of the same type still have params which differ
public Player owner; 
public Boolean blockaded; //Set to true whenever arriving fleet's ownership doesn't match planet's ownership.
public Boolean homePlanet;
}
