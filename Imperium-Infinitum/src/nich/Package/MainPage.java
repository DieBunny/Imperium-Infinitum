package nich.Package;

import nich.Classes.HexagonDrawing;
import nich.Classes.Planet;
import nich.Classes.Player;
import nich.Package.R.color;
import nich.Package.R.drawable;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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
		
		
		//generates hexgrid
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainlayout);
		HexagonDrawing hexDraw = new HexagonDrawing();
		
		if(iiglobal.galaxyGenerated==false)
		{
		XmlResourceParser xpp = this.getResources().getXml(R.xml.impinf);
		hexDraw.GetXmlPlanets(playerCount, xpp);
		for(Planet planet :hexDraw.ConvertCoordinates(playerCount))
		{
			iiglobal.planetList.add(planet);
		}
		iiglobal.galaxyGenerated=true;
		}
		
		hexDraw.DrawHex(layout, this, playerCount, iiglobal.planetList);
		
		

		
		//Displays player names somewhere on screen
		for (int i=0; i<playerCount; i++)
		{
			TextView label = new TextView(this);
			label.setTextColor(getResources().getColor(R.color.White));
			label.setTextSize(28);
			RelativeLayout.LayoutParams shareParams = new RelativeLayout.LayoutParams(180, 80);
			shareParams.leftMargin = Math.abs((int) (40));
			shareParams.topMargin = Math.abs((int) (i*40+40));
			label.setLayoutParams(shareParams);
			label.setText(iiglobal.playerList.get(i).name.toString());
			layout.addView(label);
		}
		
		//enabling Choose home planet text and arrange ships button
		if (iiglobal.turn==0)
		{
			//!!!!!!!!! have to find place after returning from ship arranging
			//have to delete HP letters afterwards
			for (Planet planet:iiglobal.planetList)
			{
				if (planet.homePlanet==true && planet.owner!=null)
				{
					ImageButton homeplanet = (ImageButton) layout.findViewWithTag(planet);
					//homeplanet.setImageResource(R.drawable.null_image);
					homeplanet.setImageResource(0);
				}
			}
			
			
			for(Player player :iiglobal.playerList)
			{
				if (player.victoryPoints==0)
				{
					TextView arrangetext = (TextView) findViewById(R.id.arrangeText);
					arrangetext.setText("Choose your home planet " + player.name);
					arrangetext.setVisibility(View.VISIBLE);
					findViewById(R.id.arrangeButton).setVisibility(View.VISIBLE);
					iiglobal.currentPlayer=player;
				}
			}
			
			//Placing HP letters
			for (Planet planet:iiglobal.planetList)
			{
				if (planet.homePlanet==true && planet.owner==null)
				{
					ImageButton homeplanet = (ImageButton) layout.findViewWithTag(planet);
					//sitaa liinija pis visu. nevar 2x likt imageresource?
					//homeplanet.setImageResource(drawable.hptransp);
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
	//because onRestoreInstanceState is called after oncreate() DAAAA
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	  savedInstanceState.putBoolean("created", true);  
	  super.onSaveInstanceState(savedInstanceState);  
	}  
	    @Override  
	public void onRestoreInstanceState(Bundle savedInstanceState) {  
	  super.onRestoreInstanceState(savedInstanceState);
	  boolean created = savedInstanceState.getBoolean("created");  
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
		Intent intent = new Intent(this, ShipArrangingRelative.class);
		startActivity(intent);
	}

}
