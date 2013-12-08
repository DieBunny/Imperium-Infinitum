package nich.Classes;

import java.util.ArrayList;
import java.util.List;

import nich.Package.ImpInfGlobal;
import nich.Package.R;
import android.R.menu;
import android.content.Context;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListPopupWindow;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class FleetClickListener implements OnClickListener, android.widget.PopupMenu.OnMenuItemClickListener{

	ImpInfGlobal iiglobal;
	Fleet fleet;
	Context context;
	RelativeLayout layout;
	Planet fleetPlanet;
	View view;
	Fleet secondFleet;
	
	@Override
	public void onClick(View v) 
	{
		fleet = (Fleet) v.getTag();
		context = v.getContext();
		view=v;
		iiglobal = ImpInfGlobal.getInstance();
		
		if(fleet.owner.equals(iiglobal.currentPlayer))
		{
			iiglobal.reachablePlanets.clear();
			layout = (RelativeLayout) v.getParent();
			PopupMenu popup = new PopupMenu(v.getContext(), v);
			
			if (iiglobal.phase==2)
			{
				iiglobal.currentFleet=fleet;
				
				//There is 2 fleets, (but you can see only one)
				if (this.CheckIfMultipleFleets())
				{
					popup.getMenu().add(Menu.NONE, 5, Menu.NONE, "  First  Fleet: " + fleet.getSize() + " ships");
					popup.getMenu().add(Menu.NONE, 6, Menu.NONE, "Second Fleet: " + secondFleet.getSize() + " ships");
				}
				
			//WYSIWYG there is one fleet. Most of the cases.
			else
			{
			//fleet has enough fuel/locations to move
			if(iiglobal.CanFlySomewhere(fleet))
			{popup.getMenu().add(Menu.NONE, 1, Menu.NONE, "Move Fleet");}
			//if fleet size >=5 and moved == false
			if(this.CanGainControl(fleet))
			{popup.getMenu().add(Menu.NONE, 2, Menu.NONE, "Gain Control");}
			//if fleet size >1 , moved==false and has enough fuel to move
			if(fleet.getSize()>1 && (fleet.moved==false && iiglobal.CanFlySomewhere(fleet)))
			{popup.getMenu().add(Menu.NONE, 3, Menu.NONE, "Split Fleet");}
			popup.getMenu().add(Menu.NONE, 4, Menu.NONE, "View Fleet");
			}
			}
			else
			{popup.getMenu().add(Menu.NONE, 4, Menu.NONE, "View Fleet");}
			
			popup.setOnMenuItemClickListener(this);
			popup.show();
		
	}
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
Toast toast = new Toast(context);
		switch (item.getItemId()) 
		{
			case 1:
				this.DrawPossibleMovements(fleet);
				break;
			case 2:
				this.GainControl(fleet);
				break;
			case 3:
				toast = Toast.makeText(context, "title: " + item.getTitle(), Toast.LENGTH_SHORT);
				toast.show();
				break;
			case 4:
				this.ViewFleet(fleet);
				break;
			case 5:
				this.CreateNewPopup1(fleet);
				iiglobal.currentFleet = fleet;
				break;
			case 6:
				this.CreateNewPopup2(secondFleet);
				iiglobal.currentFleet = secondFleet;
				break;
			case 7:
				this.DrawPossibleMovements(secondFleet);
				break;
			case 8:
				this.GainControl(secondFleet);
				break;
			case 9:
				toast = Toast.makeText(context, "title: " + item.getTitle(), Toast.LENGTH_SHORT);
				toast.show();
				break;
			case 10:
				this.ViewFleet(secondFleet);
				break;
			}
		return false;
		}
	
	public boolean CanGainControl(Fleet fleetfff)
	{
		boolean canGainControl = false;
		if(fleetfff.location.owner==null || !fleetfff.location.owner.equals(fleetfff.owner))
		{
			if(fleetfff.getSize()>=5 && fleetfff.moved==false)
			{
				canGainControl = true;
			}
		}
			return canGainControl;
	}
	
	public void DrawPossibleMovements(Fleet fleetfff)
	{
		fleetPlanet = fleetfff.location;
		int distance = 0;
		for (Planet planet:iiglobal.planetList)
		{
			int dx = fleetPlanet.xPos - planet.xPos;
			int dy = fleetPlanet.yPos - planet.yPos;
			if ((dx>0&&dy>00) || (dx<0&&dy<0))
			{distance = Math.abs(dx+dy);}
			else
			{distance = Math.max(Math.abs(dx),Math.abs(dy));}
			
			if (distance<=iiglobal.defaultmove + iiglobal.currentPlayer.fuel && distance!=0)
			{
				if(fleetfff.getSize()>=5)
				{
				this.DrawLine(planet);
				}
				
				else if(planet.owner==null || !planet.owner.equals(fleetfff.owner))
				{
					//if fleet is 1,2.. ships. If there are my fleet on neutral/enemy planet
						for(Fleet fll:iiglobal.fleetList)
						{
							if(fll.owner.equals(fleetfff.owner) && fll.location.equals(planet))
							{
								this.DrawLine(planet);
							}
						}
				}
				else if(planet.owner.equals(fleetfff.owner))
				{
					this.DrawLine(planet);
				}
			}
		}
	}
	
	public void DrawLine(Planet planet)
	{
		ImageButton planetButton = (ImageButton) layout.findViewWithTag(planet);
		RelativeLayout.LayoutParams shareparams = (LayoutParams) view.getLayoutParams();
		int fleetTop = shareparams.topMargin + shareparams.height/2;
		int fleetLeft = shareparams.leftMargin + shareparams.width/2;
		shareparams = (LayoutParams) planetButton.getLayoutParams();
		int targetPlanetTop = shareparams.topMargin + shareparams.height/2;
		int targetPlanetLeft = shareparams.leftMargin + shareparams.width/2;
		
		Line line = new Line(context);
		RelativeLayout.LayoutParams lineParams = new RelativeLayout.LayoutParams(Math.abs(targetPlanetLeft-fleetLeft), Math.abs(targetPlanetTop-fleetTop));
		lineParams.leftMargin = Math.min(targetPlanetLeft, fleetLeft);
		lineParams.topMargin = Math.min(targetPlanetTop, fleetTop);
		line.setTag("line");
		if(fleetLeft<targetPlanetLeft)
		{line.startx=0; line.endx=lineParams.width;}
		else
		{line.startx=lineParams.width;line.endx=0;}
		if(fleetTop<targetPlanetTop)
		{line.starty=0; line.endy=lineParams.height;}
		else
		{line.starty=lineParams.height;line.endy=0;}
		line.setLayoutParams(lineParams);
		line.setBackgroundColor(Color.TRANSPARENT);
		layout.addView(line);
		iiglobal.reachablePlanets.add(planet);
	}
	
	public void GainControl(Fleet fleetfff)
	{
		fleetfff.location.owner=fleetfff.owner;
		iiglobal.currentPlayer.victoryPoints++;
		iiglobal.CheckWinner(layout);
		fleetfff.moved=true;
		
		//UPDATE UI
		ImageButton homeplanet = (ImageButton) layout.findViewWithTag(fleetfff.location);
		int index = iiglobal.playerList.indexOf(fleetfff.location.owner);
		homeplanet.setImageResource(iiglobal.triangleArray[index]);
		
	}
	
	public void ViewFleet(Fleet fleetfff)
	{
		List<String> phaseList = new ArrayList<String>();
		phaseList.add("battleships: " + fleetfff.battleships);
		phaseList.add("destroyers: " + fleetfff.destroyers);
		phaseList.add("frigattes: " + fleetfff.frigates);
		ListPopupWindow listPopupWindow = new ListPopupWindow(context);
		listPopupWindow.setAdapter(new ArrayAdapter(context, R.drawable.battlephases, phaseList));
		//listPopupWindow.setBackgroundDrawable(R.drawable.battlephases);
		listPopupWindow.setAnchorView(view);
		listPopupWindow.setWidth(160);
        listPopupWindow.setHeight(130);
        listPopupWindow.setModal(true);
        listPopupWindow.show();
	}
	
	public boolean CheckIfMultipleFleets()
	{
		for (Fleet fff:iiglobal.fleetList)
		{
				if (fff.location.equals(fleet.location) && !fff.equals(fleet))
			{
				secondFleet = fff;
				return true;
			}

		}
			
		return false;
	}
	
	public void CreateNewPopup2 (Fleet fleetfff)
	{
		PopupMenu popup = new PopupMenu(view.getContext(), view);
		if(iiglobal.CanFlySomewhere(fleetfff))
		{popup.getMenu().add(Menu.NONE, 7, Menu.NONE, "Move Fleet");}
		//if fleet size >=5 and moved == false
		if(this.CanGainControl(fleetfff))
		{popup.getMenu().add(Menu.NONE, 8, Menu.NONE, "Gain Control");}
		//if fleet size >1 , moved==false and has enough fuel to move
		if(fleetfff.getSize()>1 && (fleetfff.moved==false && iiglobal.CanFlySomewhere(fleetfff)))
		{popup.getMenu().add(Menu.NONE, 9, Menu.NONE, "Split Fleet");}
		popup.getMenu().add(Menu.NONE, 10, Menu.NONE, "View Fleet");
		
		popup.setOnMenuItemClickListener(this);
		popup.show();
	}
	
	public void CreateNewPopup1 (Fleet fleetfff)
	{
		PopupMenu popup = new PopupMenu(view.getContext(), view);
		if(iiglobal.CanFlySomewhere(fleetfff))
		{popup.getMenu().add(Menu.NONE, 1, Menu.NONE, "Move Fleet");}
		//if fleet size >=5 and moved == false
		if(this.CanGainControl(fleetfff))
		{popup.getMenu().add(Menu.NONE, 2, Menu.NONE, "Gain Control");}
		//if fleet size >1 , moved==false and has enough fuel to move
		if(fleetfff.getSize()>1 && (fleetfff.moved==false && iiglobal.CanFlySomewhere(fleetfff)))
		{popup.getMenu().add(Menu.NONE, 3, Menu.NONE, "Split Fleet");}
		popup.getMenu().add(Menu.NONE, 4, Menu.NONE, "View Fleet");
		
		popup.setOnMenuItemClickListener(this);
		popup.show();
	}
	
}



/*
Line line = new Line(v.getContext());
RelativeLayout.LayoutParams lineParams = new RelativeLayout.LayoutParams(150, 150);
lineParams.leftMargin = 180;
lineParams.topMargin = 80;
line.setLayoutParams(lineParams);
line.setBackgroundColor(Color.TRANSPARENT);
*/
