package nich.Package;

import nich.Classes.DoneClickListener;
import nich.Classes.Fleet;
import nich.Classes.FleetClickListener;
import nich.Classes.HexagonDrawing;
import nich.Classes.Line;
import nich.Classes.Planet;
import nich.Classes.Player;
import nich.Classes.Resources;
import nich.Package.R.color;
import nich.Package.R.drawable;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainPage extends Activity {
	
	int playerCount = 0;
	ImpInfGlobal iiglobal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		iiglobal = ImpInfGlobal.getInstance();
		playerCount = iiglobal.playerList.size();
		
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainlayout);
		Button doneButton = (Button) findViewById(R.id.done);
		doneButton.setOnClickListener(new DoneClickListener());
		
		//generates hexgrid
		if(iiglobal.galaxyGenerated==false)
		{
		XmlResourceParser xpp = this.getResources().getXml(R.xml.impinf);
		iiglobal.hexDraw.GetXmlPlanets(playerCount, xpp);
		for(Planet planet :iiglobal.hexDraw.ConvertCoordinates(playerCount))
		{
			iiglobal.planetList.add(planet);
		}
		iiglobal.galaxyGenerated=true;
		}

		iiglobal.hexDraw.DrawHex(layout, this, playerCount, iiglobal.planetList, iiglobal.planetClickListener, iiglobal.resClickListener, iiglobal.phaseClickListener);

			
		//Writing player names on playerwindows
		for (int i=0; i<playerCount; i++)
		{
			Button playerwindow = (Button) layout.findViewWithTag("playerwindow"+i);
			playerwindow.setText(iiglobal.playerList.get(i).name.toString());
			playerwindow.setTag(iiglobal.playerList.get(i).name.toString()+"window");
			playerwindow.setCompoundDrawablesWithIntrinsicBounds( iiglobal.playerList.get(i).image, 0, 0, 0);
			playerwindow.setVisibility(View.VISIBLE);
			View tokenCircle = layout.findViewWithTag("token"+i);
			tokenCircle.setTag(iiglobal.playerList.get(i).name.toString()+"token");
		}
		
		
		if (iiglobal.turn==0)
		{
			//Removing Yellow rings, adding Fleets instead
			for (Planet planet:iiglobal.planetList)
			{
				if (planet.homePlanet==true && planet.owner!=null)
				{
					View homeplanetring = (View) layout.findViewWithTag(planet.homePlanet);
					layout.removeView(homeplanetring);
					ImageButton homeplanet = (ImageButton) layout.findViewWithTag(planet);
					int index = iiglobal.playerList.indexOf(planet.owner);
					homeplanet.setImageResource(iiglobal.triangleArray[index]);
					//+ ADDING SHIP IMAGE
					RelativeLayout.LayoutParams shareparams = (LayoutParams) homeplanet.getLayoutParams();
					int topmargin = shareparams.topMargin;
					int leftmargin = shareparams.leftMargin;
					RelativeLayout.LayoutParams shareParams = new RelativeLayout.LayoutParams(40, 40);
					View fleetview = new View(layout.getContext());
					for(Fleet fleet : iiglobal.fleetList)
					{
						if(fleet.location.equals(planet))
						{
							fleetview.setTag(fleet);
							fleetview.setBackgroundResource(R.drawable.circleshape);
							GradientDrawable shapeDrawable = (GradientDrawable)fleetview.getBackground();
							shapeDrawable.setColor(getResources().getColor(iiglobal.colorArray[iiglobal.playerList.indexOf(fleet.owner)]));
							shareParams.leftMargin = leftmargin +40;
							shareParams.topMargin = topmargin+40;
							fleetview.setLayoutParams(shareParams);
							fleetview.setOnClickListener(iiglobal.fleetClickListener);
							layout.addView(fleetview);	
						}
					}
				}
			}
			
			int playersleft=0;
			
			//enabling Choose home planet text
			for(Player player :iiglobal.playerList)
			{
				if (player.victoryPoints==0)
				{ 
					playersleft++;
					TextView arrangetext = (TextView) findViewById(R.id.arrangeText);
					arrangetext.setText("Choose your home planet " + player.name);
					arrangetext.setVisibility(View.VISIBLE);
					iiglobal.currentPlayer=player;
				}
			}
			Button playerwindow = (Button) layout.findViewWithTag(iiglobal.currentPlayer.name.toString()+"window");
			playerwindow.setEnabled(true);
			
			if (playersleft==0)
			{
				iiglobal.turn=1;
				iiglobal.tokenOwner = iiglobal.playerList.get(0);
				iiglobal.SetActiveToken(layout);
				iiglobal.currentPlayer = iiglobal.tokenOwner;
				iiglobal.phase=0;
				iiglobal.ChangeResourceVisibility(layout);
				layout.findViewWithTag(iiglobal.phaseArray[iiglobal.phase]).setEnabled(true);
			}
			else
			{
			
			//circle ring on screen
			for (Planet planet:iiglobal.planetList)
			{
				if (planet.homePlanet==true && planet.owner==null)
				{
					ImageButton homeplanet = (ImageButton) layout.findViewWithTag(planet);
					RelativeLayout.LayoutParams shareparams = (LayoutParams) homeplanet.getLayoutParams();
					int topmargin = shareparams.topMargin;
					int leftmargin = shareparams.leftMargin;
					
					
					RelativeLayout.LayoutParams shareParams = new RelativeLayout.LayoutParams(90, 90);
					View ringview = new View(layout.getContext());
					ringview.setTag(planet.homePlanet);
					ringview.setBackgroundResource(R.drawable.circleshape);
					shareParams.leftMargin = leftmargin;
					shareParams.topMargin = topmargin;
					ringview.setLayoutParams(shareParams);
					layout.addView(ringview);
				}
			}
					
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
	iiglobal.planetList.clear();
	iiglobal.fleetList.clear();
	this.finish();
	}
	
	
	//recreating saved states doesn`t work DEBUG THIS!!!!!!!
	//because onRestoreInstanceState is called after oncreate()
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {  
	  super.onSaveInstanceState(savedInstanceState); 
	  savedInstanceState.putString("testvalue", "quadrantxxx");
	}  
	    @Override  
	public void onRestoreInstanceState(Bundle savedInstanceState) {  
	  super.onRestoreInstanceState(savedInstanceState);
	  String testvalue = savedInstanceState.getString("testvalue");
		Toast toast = Toast.makeText(this, testvalue +"onRestoreInstanceState()", Toast.LENGTH_SHORT);
		toast.show();
	}
	    
	    @Override
        public void onConfigurationChanged(Configuration newConfig) {

          super.onConfigurationChanged(newConfig);

          if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
              //your code
          } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        	  //your code
          }
        }
	
	public void GoToArrangeShipsPage(View v)
	{
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainlayout);
		layout.removeAllViews();
		Intent intent = new Intent(this, ShipArrangingRelative.class);
		startActivity(intent);
	}
	
	public void OnLayoutClick (View v)
	{
		RelativeLayout layout = (RelativeLayout) v;
		for (int i=layout.getChildCount()-1; i>layout.getChildCount()-20; i--)
		{
			if(layout.getChildAt(i).getTag().equals("line"))
			{layout.removeViewAt(i);}
		}
		iiglobal.reachablePlanets.clear();
	}

}
