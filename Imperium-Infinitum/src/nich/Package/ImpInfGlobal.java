package nich.Package;

import java.util.ArrayList;
import java.util.List;

import nich.Classes.Fleet;
import nich.Classes.FleetClickListener;
import nich.Classes.HexagonDrawing;
import nich.Classes.PhasesClickListener;
import nich.Classes.Planet;
import nich.Classes.PlanetClickListener;
import nich.Classes.Player;
import nich.Classes.ResourceClickListener;
import nich.Classes.Resources;

import android.app.Application;
import android.opengl.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
	 public int phase =3;
	 public int victoryPoints = 10;
	 public Player currentPlayer;
	 public Planet currentPlanet1;
	 public Planet currentPlanet2;
	 public Fleet currentFleet;
	 public boolean galaxyGenerated = false;
	 public HexagonDrawing hexDraw = new HexagonDrawing();
	 public PlanetClickListener planetClickListener = new PlanetClickListener();
	 public ResourceClickListener resClickListener = new ResourceClickListener(); 
	 public PhasesClickListener phaseClickListener = new PhasesClickListener();
	 public FleetClickListener fleetClickListener = new FleetClickListener();
	 public int[] colorArray = {R.color.Red,R.color.Blue,R.color.Green,R.color.Gray};
	 public int[] triangleArray = {R.drawable.redtriangle,R.drawable.bluetriangle,R.drawable.greentriangle,R.drawable.graytriangle};
	 public String[] phaseArray = {"resources","research","movement","discard"};
	 public Player tokenOwner;
	 public List<Planet> reachablePlanets = new ArrayList<Planet>();
	 
	 public void GetNextPlayer()
	 {
		 int index = this.playerList.indexOf(this.currentPlayer);
		 if (index==this.playerList.size()-1)
		 {
			 index =0;
		 }
		 else
		 {index++;}
		 this.currentPlayer = this.playerList.get(index);
	 }
	 
	 public void GetNextPhase()
	 {
		 if (this.phase==3)
		 {
			 phase = 0;
		 }
		 else
		 {phase++;}
	 }
	 
	 public void GetNextTokenOwner()
	 {
		 int index = this.playerList.indexOf(this.tokenOwner);
		 if (index==this.playerList.size()-1)
		 {
			 index =0;
		 }
		 else
		 {index++;}
		 this.tokenOwner = this.playerList.get(index);
	 }	 
	 
	 public void CheckWinner(RelativeLayout layout)
	 {
		 for(Player player:this.playerList)
		 {
			 if (player.victoryPoints>=this.victoryPoints)
			 {
					Toast toast = Toast.makeText(layout.getContext(),"! "+ player.name + " WINS!!!", Toast.LENGTH_LONG);
					toast.show();
			 }
		 }
	 }
	 
	 public void ResolvePlayerFuelResearch()
	 {
			for(Resources res:this.currentPlayer.playerRes)
			{
				if(res.type.equals("fuel"))
				{this.currentPlayer.fuel=this.currentPlayer.fuel + res.value;}
				if(res.type.equals("research"))
				{this.currentPlayer.research=this.currentPlayer.research + res.value;}
			}
			//wanted to delete fuel and research, but exception
	 }

	 public void SetActivePlayerWindow(RelativeLayout layout)
	 {
		 //tag "JanisWindow"
		 Button playerwindow = new Button(layout.getContext());
		 for (Player player : this.playerList)
		 {
			 playerwindow = (Button) layout.findViewWithTag(player.name.toString()+"window");
			 if (player.equals(this.currentPlayer))
			 {
				 playerwindow.setEnabled(true);
			 }
			 else
			 {
				 playerwindow.setEnabled(false);
			 }
		 }
	 }
	 
	 public void SetActivePhaseWindow(RelativeLayout layout)
	 {
		 //tag "resources","research","movement","discard"
			//layout.findViewWithTag(iiglobal.phaseArray[iiglobal.phase]).setEnabled(true);
		 Button phasewindow = new Button(layout.getContext());
		 for (int i=0; i<this.phaseArray.length; i++)
		 {
			 phasewindow = (Button) layout.findViewWithTag(this.phaseArray[i]);
			 if(i==this.phase)
			 {
				 phasewindow.setEnabled(true);
			 }
			 else
			 {
				 phasewindow.setEnabled(false);
			 }
		 }
	 }

	 public void SetActiveToken(RelativeLayout layout)
	 {
		 //tag "Janistoken"
		 View tokenCircle = new View(layout.getContext());
		 for (Player player : this.playerList)
		 {
			 tokenCircle = (View) layout.findViewWithTag(player.name.toString()+"token");
			 if (player.equals(this.tokenOwner))
			 {
				 tokenCircle.setVisibility(View.VISIBLE);
			 }
			 else
			 {
				 tokenCircle.setVisibility(View.GONE);
			 }
		 }
	 }
	 
	 public void RestoreResourceValues()
	 {
		 for(Planet planet:this.planetList)
		 {
			 if (planet.owner!=null)
			 {
				 if(planet.owner.equals(this.currentPlayer))
				 {
				 for(Resources res:planet.planetRes)
				 {
					 res.value=1;
				 }
				 }
			 }
		 }
	 }
	 
	 public void HandOutShips()
	 {
		 //TO DO add ships to fleets
		 //create new fleet if first ship on planet, UPDATE UI
		 //search for not-blockaded planets and discard ships if none found
		 //clear player resource list afterwards
		 this.currentPlayer.playerRes.clear();
	 }
	 
	 public void GainPlanetControl()
	 {
		 //TO DO gain control if 5 ships skipped turn  DONE in MAINPAGE.JAVA
		 //UPDATE UI, planet with triangle
		 //Fleet can retreat ALWAYS (change moved boolean to true (false->true, true->true(if gained control,moved in his turn)
	 }
	 
	 public void FleetsMovedFalse()
	 {
		 for (Fleet fleet:this.fleetList)
		 {
			 fleet.moved=false;
		 }
	 }
	 
	 public void DeleteMovementLines(RelativeLayout layout)
	 {
			for (int i=layout.getChildCount()-1; i>layout.getChildCount()-10; i--)
			{
				if(layout.getChildAt(i).getTag().equals("line"))
				{layout.removeViewAt(i);}
			}
			this.reachablePlanets.clear();
	 }
	 
	 public void RetrieveBlockades()
	 {
		 for (Planet planet : this.planetList)
		 {
			 planet.blockaded = false;
		 }
		 
		 for (Fleet fleet : this.fleetList)
		 {
			 if (!fleet.owner.equals(fleet.location.owner))
			 {
				 fleet.location.blockaded=true;
			 }
		 }
	 }
	 
	 public void DiscardFuel()
	 {
		 this.currentPlayer.fuel=0;
	 }
	 
	 
	 public void ChangeResourceVisibility(RelativeLayout layout)
	 {
		 ImageView fuelImage = (ImageView) layout.findViewById(R.id.fuelimage);
		 int VisibilityValue = 0;
		 if (fuelImage.getVisibility()==View.VISIBLE)
		 {
			 VisibilityValue = View.GONE;
		 }
		 else if (fuelImage.getVisibility()==View.GONE)
		 {
			 VisibilityValue = View.VISIBLE;
		 }
		 layout.findViewById(R.id.fuelimage).setVisibility(VisibilityValue);
		 layout.findViewById(R.id.fuelCount).setVisibility(VisibilityValue);
		 layout.findViewById(R.id.researchimage).setVisibility(VisibilityValue);
		 layout.findViewById(R.id.researchCount).setVisibility(VisibilityValue);
		 layout.findViewById(R.id.battleshipimage).setVisibility(VisibilityValue);
		 layout.findViewById(R.id.battleshipCount).setVisibility(VisibilityValue);
		 layout.findViewById(R.id.destroyerimage).setVisibility(VisibilityValue);
		 layout.findViewById(R.id.destroyerCount).setVisibility(VisibilityValue);
		 layout.findViewById(R.id.frigatteimage).setVisibility(VisibilityValue);
		 layout.findViewById(R.id.frigatteCount).setVisibility(VisibilityValue);
		 
	 }
	 
	 public void SetZeroUIResources(RelativeLayout layout)
	 {
		 TextView textViewCount = (TextView) layout.findViewById(R.id.fuelCount);
		 textViewCount.setText("0");
		 textViewCount = (TextView) layout.findViewById(R.id.researchCount);
		 textViewCount.setText("0");
		 textViewCount = (TextView) layout.findViewById(R.id.battleshipCount);
		 textViewCount.setText("0");
		 textViewCount = (TextView) layout.findViewById(R.id.destroyerCount);
		 textViewCount.setText("0");
		 textViewCount = (TextView) layout.findViewById(R.id.frigatteCount);
		 textViewCount.setText("0");
	 }
}
