package nich.Package;

import java.util.ArrayList;
import java.util.Arrays;
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
import android.graphics.drawable.GradientDrawable;
import android.opengl.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

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
	 public int defaultmove = 4; //CHANGE TO 3 - minimum movement without fuel
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
	 
	 public void HandOutShips(RelativeLayout layout)
	 {
		 //TO DO add ships to fleets
		 //create new fleet if first ship on planet, UPDATE UI
		 //search for not-blockaded planets and discard ships if none found
		 //clear player resource list afterwards
		 for (Resources res : this.currentPlayer.playerRes)
		 {if(Arrays.asList("battleship", "destroyer", "frigatte").contains(res.type))
		 {
			 if (!res.linkedPlanet.blockaded)
			 {
				 this.IncreaseOrCreateFleet(res.linkedPlanet, res, layout);
			 }
			 else
			 {
				 //this planet blockaded, search another planet
				 boolean hasValidPlace = false;
				 for (Planet planet:this.planetList)
				 {
					 if(planet.owner!=null)
					 {
						 if(planet.owner.equals(this.currentPlayer) && !planet.blockaded)
						 {
							 for (Resources rrr:planet.planetRes)
							 {
								 if (rrr.type.equals(res.type))
								 {
									 hasValidPlace = true;
									 this.reachablePlanets.add(planet);
								 }
								 
							 }
						 }
					 }
				 }
				 if (!hasValidPlace)
				 {
					 Toast toast = Toast.makeText(layout.getContext(), "DISCARDING " + res.type, Toast.LENGTH_SHORT);
					 toast.show();
				 }
				 else
				 {
					 this.IncreaseOrCreateFleet(this.FindClosestPlanet(res.linkedPlanet), res, layout);
				 }

			 }
		 }
		 }
		 
		 this.currentPlayer.playerRes.clear();
	 }
	 
	 private Planet FindClosestPlanet(Planet originalPlanet)
	 {
		 Planet closestPlanet = new Planet();
		 int hexcount = 50;
		 int distance;
		 for (Planet rplanet: this.reachablePlanets)
		 {
			 int dx = originalPlanet.xPos - rplanet.xPos;
				int dy = originalPlanet.yPos - rplanet.yPos;
				if ((dx>0&&dy>00) || (dx<0&&dy<0))
				{distance = Math.abs(dx+dy);}
				else
				{distance = Math.max(Math.abs(dx),Math.abs(dy));}
				
				if (distance<hexcount)
				{
					hexcount=distance;
					closestPlanet = rplanet;
				}
		 }
		 
		 this.reachablePlanets.clear();
		 return closestPlanet;
	 }
	 
	 private void IncreaseOrCreateFleet(Planet fleetPlanet, Resources resource, RelativeLayout layout)
	 {
		 boolean hasFleetThere = false;
		 for (Fleet fleet:this.fleetList)
		 {
			 if (fleet.location.equals(fleetPlanet))
			 {
				 hasFleetThere = true;
				 if (resource.type.equals("battleship"))
				 {fleet.battleships++;}
				 if (resource.type.equals("destroyer"))
				 {fleet.destroyers++;}
				 if (resource.type.equals("frigatte"))
				 {fleet.frigates++;}
			 }
		 }
		 
		 if (!hasFleetThere)
		 {
			 //Create new fleet with one ship, add to FleetList
			 Fleet newFleet = new Fleet();
			newFleet.battleships = 0;
			newFleet.destroyers = 0;
			newFleet.frigates = 0;
			 if (resource.type.equals("battleship"))
			 {newFleet.battleships++;}
			 if (resource.type.equals("destroyer"))
			 {newFleet.destroyers++;}
			 if (resource.type.equals("frigatte"))
			 {newFleet.frigates++;}
			newFleet.location = fleetPlanet;
			newFleet.owner = this.currentPlayer;
			this.fleetList.add(newFleet);
			
			// update UI - add Fleet to layout
			ImageButton planetButton = (ImageButton) layout.findViewWithTag(fleetPlanet);
			RelativeLayout.LayoutParams shareparams = (LayoutParams) planetButton.getLayoutParams();
			int topmargin = shareparams.topMargin;
			int leftmargin = shareparams.leftMargin;
			RelativeLayout.LayoutParams shareParams = new RelativeLayout.LayoutParams(40, 40);
			View fleetview = new View(layout.getContext());
			fleetview.setTag(newFleet);
			fleetview.setBackgroundResource(R.drawable.circleshape);
			GradientDrawable shapeDrawable = (GradientDrawable)fleetview.getBackground();
			shapeDrawable.setColor(getResources().getColor(this.colorArray[this.playerList.indexOf(newFleet.owner)]));
			shareParams.leftMargin = leftmargin +40;
			shareParams.topMargin = topmargin+40;
			fleetview.setLayoutParams(shareParams);
			fleetview.setOnClickListener(this.fleetClickListener);
			layout.addView(fleetview);	
		 }
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
	 
	 public void JoinFleets(Fleet firstFleet, Fleet secondFleet)
	 {
		 firstFleet.location = secondFleet.location;
		 firstFleet.battleships = firstFleet.battleships + secondFleet.battleships;
		 firstFleet.destroyers = firstFleet.destroyers + secondFleet.destroyers;
		 firstFleet.frigates = firstFleet.frigates + secondFleet.frigates;
		 
		 this.fleetList.remove(secondFleet);
		 this.currentFleet = firstFleet;
	 }
	 
	 public void FindAndJoinFleets(RelativeLayout layout)
	 {
		 int fleetCount = 0;
		 Fleet firstFleet = new Fleet();
		 Fleet secondFleet = new Fleet();
		 
		 for (Planet planet:this.planetList)
		 {
			 if (planet.owner!=null && planet.owner.equals(this.currentPlayer))
			 {
				 for(Fleet fleet:this.fleetList)
				 {
					 if(fleet.location.equals(planet) && fleet.owner.equals(this.currentPlayer))
					 {
						 fleetCount++;
						 if(fleetCount==1)
						 {firstFleet = fleet;}
						 else if(fleetCount==2)
						 {secondFleet=fleet;}
					 }
					 
				 }
				 if (fleetCount==2)
				 {
					 this.JoinFleets(firstFleet, secondFleet);
					 layout.removeView(layout.findViewWithTag(secondFleet));
				 }
				 
			 }
			 fleetCount=0;
			 
		 }
		 
	 }

	 public boolean CanFlySomewhere(Fleet fleet)
	 {
			Planet fleetPlanet = fleet.location;
			int distance = 0;
			boolean canFlySomewhere = false;
			if (fleet.moved==true)
			{return canFlySomewhere;}
			for (Planet planet:this.planetList)
			{
				int dx = fleetPlanet.xPos - planet.xPos;
				int dy = fleetPlanet.yPos - planet.yPos;
				if ((dx>0&&dy>00) || (dx<0&&dy<0))
				{distance = Math.abs(dx+dy);}
				else
				{distance = Math.max(Math.abs(dx),Math.abs(dy));}
				
				if (distance<=defaultmove + this.currentPlayer.fuel && distance!=0)
				{
					if(fleet.getSize()>=5)
					{
					canFlySomewhere=true;
					return canFlySomewhere;
					}
					
					else if(planet.owner==null || !planet.owner.equals(fleet.owner))
					{
						//if fleet is 1,2.. ships. If there are my fleet on neutral/enemy planet
							for(Fleet fll:this.fleetList)
							{
								if(fll.owner.equals(fleet.owner) && fll.location.equals(planet))
								{
									canFlySomewhere=true;
									return canFlySomewhere;
								}
							}
					}
					else if(planet.owner.equals(fleet.owner))
					{
						canFlySomewhere=true;
						return canFlySomewhere;
					}
				}
			}
			return canFlySomewhere;
	 }
}
