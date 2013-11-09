package nich.Package;

import java.util.ArrayList;
import java.util.List;

import nich.Classes.Fleet;
import nich.Classes.Planet;
import nich.Classes.Player;

import android.app.Application;

//This is our global class!!! We can access this everywhere in application,
//from all activities
public class ImpInfGlobal extends Application {

	private static ImpInfGlobal instance = null;
	 
	 @Override
	 public void onCreate() {
	  super.onCreate();
	  instance = this;
	 }
	 
	 public static ImpInfGlobal getInstance() {
	  return instance;
	 }
	 
	 public List<Player> playerList = new ArrayList<Player>();
	 public List<Planet> planetList = new ArrayList<Planet>();
	 public List<Fleet> fleetList = new ArrayList<Fleet>();
	 
	 public int turn = 0;
	 public Player currentPlayer;
	 public Planet currentPlanet1;
	 public Planet currentPlanet2;
	 boolean galaxyGenerated = false;
}
