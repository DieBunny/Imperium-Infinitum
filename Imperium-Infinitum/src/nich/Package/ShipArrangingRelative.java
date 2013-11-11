package nich.Package;

import nich.Classes.Fleet;
import nich.Classes.Planet;
import nich.Package.R.drawable;
import android.os.Bundle;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ShipArrangingRelative extends Activity {
	
	int oceantop = 70; //150
	int oceanleft = 40;	//160
	int volcantop = 70; //150
	int volcanleft = 40; //500
	ImpInfGlobal iiglobal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ship_arranging_relative);
		iiglobal = ImpInfGlobal.getInstance();
		
		Planet planet1 = iiglobal.currentPlanet1;
		Planet planet2 = this.getSecondPlanet(planet1);
		this.setPlanetImages(planet1, planet2);
		
		
		findViewById(R.id.battleship1).setOnTouchListener(new MyTouchListener());
		findViewById(R.id.battleship2).setOnTouchListener(new MyTouchListener());
		findViewById(R.id.battleship3).setOnTouchListener(new MyTouchListener());
		findViewById(R.id.battleship4).setOnTouchListener(new MyTouchListener());
		findViewById(R.id.destroyer1).setOnTouchListener(new MyTouchListener());
		findViewById(R.id.destroyer2).setOnTouchListener(new MyTouchListener());
		findViewById(R.id.destroyer3).setOnTouchListener(new MyTouchListener());
		findViewById(R.id.destroyer4).setOnTouchListener(new MyTouchListener());
		findViewById(R.id.frigatte1).setOnTouchListener(new MyTouchListener());
		findViewById(R.id.frigatte2).setOnTouchListener(new MyTouchListener());
		findViewById(R.id.frigatte3).setOnTouchListener(new MyTouchListener());
		findViewById(R.id.frigatte4).setOnTouchListener(new MyTouchListener());
		
		findViewById(R.id.leftPlanetLayout).setOnDragListener(new MyDragListener());
		findViewById(R.id.rightPlanetLayout).setOnDragListener(new MyDragListener());
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ship_arranging_relative, menu);
		return true;
	}
	
	private Planet getSecondPlanet (Planet planet1)
	{
		for (Planet planet:iiglobal.planetList)
		{
			int distance = 0;
			if (planet.homePlanet==true && planet.owner==null)
			{
				 int dx = planet1.xPos - planet.xPos;
				 int dy = planet1.yPos - planet.yPos;
				 if ((dx>0&&dy>00) || (dx<0&&dy<0))
				 {distance = Math.abs(dx+dy);}
				 else
				 {distance = Math.max(Math.abs(dx),Math.abs(dy));}
			}
			
			if (distance == 4)
			{
				iiglobal.currentPlanet2=planet;
				return planet;}
		}
		
		//shouldn`t go this far
		return null;
	}
	
	private void setPlanetImages (Planet planet1, Planet planet2)
	{
		if (planet1.xPos < planet2.xPos)
		{
			findViewById(R.id.leftPlanetLayout).setTag(planet1);
			findViewById(R.id.rightPlanetLayout).setTag(planet2);
			if(planet1.planetType.equals("Oceanic"))
			{
			findViewById(R.id.leftplanet).setBackgroundResource(drawable.mediumoceanic);
			findViewById(R.id.rightplanet).setBackgroundResource(drawable.mediumvolcanic);
			}
			if(planet1.planetType.equals("Volcanic"))
			{
			findViewById(R.id.leftplanet).setBackgroundResource(drawable.mediumvolcanic);
			findViewById(R.id.rightplanet).setBackgroundResource(drawable.mediumoceanic);
			}
		}
		else if (planet2.xPos < planet1.xPos)
		{
			findViewById(R.id.leftPlanetLayout).setTag(planet2);
			findViewById(R.id.rightPlanetLayout).setTag(planet1);
			if(planet2.planetType.equals("Oceanic"))
			{
			findViewById(R.id.leftplanet).setBackgroundResource(drawable.mediumoceanic);
			findViewById(R.id.rightplanet).setBackgroundResource(drawable.mediumvolcanic);
			}
			if(planet2.planetType.equals("Volcanic"))
			{
			findViewById(R.id.leftplanet).setBackgroundResource(drawable.mediumvolcanic);
			findViewById(R.id.rightplanet).setBackgroundResource(drawable.mediumoceanic);
			}
		}
	}
	
	public void DoneButtonClicked (View v)
	{
		RelativeLayout leftLayout = (RelativeLayout) findViewById(R.id.leftPlanetLayout);
		RelativeLayout rightLayout = (RelativeLayout) findViewById(R.id.rightPlanetLayout);

		int battleshcount = 0;
		int destroyercount =0;
		int friggcount =0;
		
		for (int i =0; i< leftLayout.getChildCount(); i++)
		{
		if (leftLayout.getChildAt(i).getTag()!=null)
		{
			String ship = leftLayout.getChildAt(i).getTag().toString();
				if (ship.equals("battleship"))
				{
					battleshcount++;
				}
				if (ship.equals("destroyer"))
				{
					destroyercount++;
				}
				if (ship.equals("frigatte"))
				{
					friggcount++;
				}
			}
			
		}

			Fleet newFleet = new Fleet();
			newFleet.battleships = battleshcount;
			newFleet.destroyers = destroyercount;
			newFleet.frigates = friggcount;
			newFleet.location = (Planet) leftLayout.getTag();
			newFleet.owner = iiglobal.currentPlayer;
			iiglobal.fleetList.add(newFleet);
			battleshcount = 0;
			destroyercount =0;
			friggcount =0;
			
			for (int i =0; i< rightLayout.getChildCount(); i++)
			{
				if (rightLayout.getChildAt(i).getTag()!=null)
				{
					String ship = rightLayout.getChildAt(i).getTag().toString();
				
					if (ship.equals("battleship"))
					{
						battleshcount++;
					}
					if (ship.equals("destroyer"))
					{
						destroyercount++;
					}
					if (ship.equals("frigatte"))
					{
						friggcount++;
					}
				}
				
			}

				Fleet newFleet1 = new Fleet();
				newFleet1.battleships = battleshcount;
				newFleet1.destroyers = destroyercount;
				newFleet1.frigates = friggcount;
				newFleet1.location = (Planet) rightLayout.getTag();
				newFleet1.owner = iiglobal.currentPlayer;
				iiglobal.fleetList.add(newFleet1);

				iiglobal.currentPlayer.victoryPoints = 2;
			iiglobal.currentPlanet1.owner = iiglobal.currentPlayer;
			iiglobal.currentPlanet2.owner = iiglobal.currentPlayer;
				
		//strangely intent here crashes application

		//Intent intent = new Intent(ShipArrangingRelative.this, MainPage.class);
		Intent intent = new Intent(v.getContext(), MainPage.class);
		startActivity(intent);
			//this.finish();
	}
	
	  private final class MyTouchListener implements OnTouchListener {
		    public boolean onTouch(View view, MotionEvent motionEvent) {
		      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
		        ClipData data = ClipData.newPlainText("", "");
		        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
		        view.startDrag(data, shadowBuilder, view, 0);
		        view.setVisibility(View.INVISIBLE);
		        return true;
		      } else {
		        return false;
		      }
		    }
		  }
	  
	  
	  
	  class MyDragListener implements OnDragListener {
		    

		    @Override
		    public boolean onDrag(View v, DragEvent event) {
		      int action = event.getAction();
		      switch (event.getAction()) {
		      case DragEvent.ACTION_DRAG_STARTED:
		        // do nothing
		        break;
		      case DragEvent.ACTION_DRAG_ENTERED:

		        break;
		      case DragEvent.ACTION_DRAG_EXITED:

		        break;
		      case DragEvent.ACTION_DROP:
		        // Dropped, reassign View to ViewGroup
		        View shipView = (View) event.getLocalState();
		        ViewGroup owner = (ViewGroup) shipView.getParent();
		        owner.removeView(shipView);
		        RelativeLayout container = (RelativeLayout) v;
		        container.addView(shipView);
		        shipView.setVisibility(View.VISIBLE);
		        
		        MarginLayoutParams marginParams = new MarginLayoutParams(shipView.getLayoutParams()); 
		        Planet tag = (Planet) container.getTag();
		        
		        if (tag.equals(iiglobal.currentPlanet1)){
		        marginParams.setMargins(oceanleft, oceantop, 0, 0);
		        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
		        shipView.setLayoutParams(layoutParams); 
		        if(oceanleft<190)
		        {oceanleft +=50;}
		        else
		        {oceanleft = 40;
		        oceantop+=50;}
		        }
		        if (tag.equals(iiglobal.currentPlanet2)){
		        marginParams.setMargins(volcanleft, volcantop, 0, 0);
			    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
			    shipView.setLayoutParams(layoutParams); 
			    if(volcanleft<190)
		        {volcanleft +=50;}
		        else
		        {volcanleft = 40;
		        volcantop+=50;}
		        }
		        
		        
		        break;
		      case DragEvent.ACTION_DRAG_ENDED:

		      default:
		        break;
		      }
		      return true;
		    }
		  }


}
