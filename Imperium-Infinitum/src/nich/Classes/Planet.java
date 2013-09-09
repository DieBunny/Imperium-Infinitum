package nich.Classes;

public class Planet {
public Resources planetRes;
public CombatPhases combatPhases;
public int xPos;
public int yPos;
public String planetType; //This is type used for visuals. There are 3 types in total. Fuel/Res Ship/Res Fuel/Ship. Planets of the same type still have params which differ
public Player owner; //0 = neutral, 1 = 1st player etc.
public Boolean blockaded; //Set to true whenever arriving fleet's ownership doesn't match planet's ownership.
}
