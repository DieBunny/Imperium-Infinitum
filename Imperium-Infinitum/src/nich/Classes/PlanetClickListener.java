package nich.Classes;

import nich.Package.ImpInfGlobal;
import nich.Package.R;
import nich.Package.ShipArrangingRelative;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

@SuppressLint("ResourceAsColor")
public class PlanetClickListener implements OnClickListener{

	ImpInfGlobal iiglobal;
	Context context;
	RelativeLayout layout;
	Planet clickedPlanet;
	View view;
	
	@Override
	public void onClick(final View v) {
		iiglobal = ImpInfGlobal.getInstance();
		clickedPlanet = (Planet) v.getTag();
		layout = (RelativeLayout) v.getParent();
		context = v.getContext();
		view=v;
		
		if (iiglobal.turn==0)
		{
			if(clickedPlanet.homePlanet==true)
			{
				final Context context = v.getContext();
				CharSequence text = "";
				if(clickedPlanet.owner!=null)
				{
					text = "This is " + clickedPlanet.owner.name +"`s home planet";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				else
				{
					Builder dialog = new AlertDialog.Builder(v.getContext());
					dialog.setTitle("Choose home planets");
					dialog.setMessage("Choose these two planets as home planets AND arrange ships?");
					dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) 
						{
							iiglobal.currentPlanet1 = clickedPlanet;
							Intent intent = new Intent(context, ShipArrangingRelative.class);
							context.startActivity(intent);
						}
					}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});

					dialog.show();
				
				}
			}
			else
			{
				Context context = v.getContext();
			CharSequence text = "You have to choose home planet!";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			}
		}
		//it is not first turn
		else
		{
			if(iiglobal.phase==0)
			{
				//TO DO in resources phase onPlanetClick
			}
			if(iiglobal.phase==1)
			{
				//TO DO in research phase onPlanetClick
			}
			if(iiglobal.phase==2)
			{
				this.MoveLogic();
			}
			if(iiglobal.phase==3)
			{
				//TO DO in discard phase onPlanetClick
			}
		}
	}
	
	
	public void MoveLogic()
	{
		if(iiglobal.reachablePlanets.contains(clickedPlanet))
		{
			iiglobal.currentPlanet1 = iiglobal.currentFleet.location;
			iiglobal.currentPlanet2 = clickedPlanet;
			boolean hasEnemyFleetThere = false;
			boolean hasFriendlyFleetThere = false;
			int fleetCount = 0;
			Fleet secondFleet = new Fleet();
			for (Fleet fleet:iiglobal.fleetList)
				{
					if(fleet.location.equals(clickedPlanet))
					{
						if(!fleet.owner.equals(iiglobal.currentPlayer))
						{hasEnemyFleetThere = true;}
						else if(fleet.owner.equals(iiglobal.currentPlayer))
						{
						hasFriendlyFleetThere=true; 
						fleetCount++;
						secondFleet = fleet;
						}
					}
				}
			if (fleetCount>1)
			{secondFleet = this.GetSecondMovedFleet();}
			
			if (hasEnemyFleetThere)
			{
				//BATTLE HERE!!!
				Toast toast = Toast.makeText(context, "BATTLE BEGINS between " +iiglobal.currentPlayer.name + " and " + clickedPlanet.owner.name, Toast.LENGTH_SHORT);
				toast.show();
			}
			else if (hasFriendlyFleetThere)
			{
				//JOINING FLEETS HERE!!!
				Toast toast = Toast.makeText(context, "Joining fleets " +iiglobal.currentFleet.location.planetType + " and " + clickedPlanet.planetType, Toast.LENGTH_SHORT);
				toast.show();
				iiglobal.currentFleet.moved=true;
				iiglobal.currentFleet.location = clickedPlanet;
				this.JoinFleets(secondFleet);
				this.UpdatingFleetUI(secondFleet);
				this.ReleaseBlockadedPlanet();
			}
			else
			{
			iiglobal.currentFleet.location=clickedPlanet;
			iiglobal.currentFleet.moved=true;
			this.UpdatingFleetUI(null);
			this.ReleaseBlockadedPlanet();
				//MOVING, no battle, blocking enemy planet
				if(clickedPlanet.owner!=null && !clickedPlanet.owner.equals(iiglobal.currentPlayer))
				{
					clickedPlanet.blockaded=true;
					clickedPlanet.owner.victoryPoints--;
				}
			}
		}
	}
	
	public void UpdatingFleetUI(final Fleet secondFleet)
	{
		AnimationSet set = new AnimationSet(true);
		int endx;
		int endy;
		ImageButton planetButton = (ImageButton) layout.findViewWithTag(clickedPlanet);
		View fleetview  = layout.findViewWithTag(iiglobal.currentFleet);
		RelativeLayout.LayoutParams shareparams = (LayoutParams) fleetview.getLayoutParams();
		int fleetTop = shareparams.topMargin;
		int fleetLeft = shareparams.leftMargin;
		shareparams = (LayoutParams) planetButton.getLayoutParams();
		int targetPlanetTop = shareparams.topMargin + 40;
		int targetPlanetLeft = shareparams.leftMargin + 40;
		
		endx = targetPlanetLeft - fleetLeft;
		endy = targetPlanetTop - fleetTop;
		
		Animation animation = new TranslateAnimation(0, endx,0, endy);  //Change this integer values based on our requirements.
		animation.setDuration(1500);
		 //animation.setFillAfter(true);
		 animation.setFillEnabled(false);
		 
		 animation.setAnimationListener(new AnimationListener()
		 {
			 @Override
			 public void onAnimationEnd(Animation arg0) {
				 //to avoid flicker at the animation end
				 layout.findViewWithTag(iiglobal.currentFleet).clearAnimation();
		
				 RelativeLayout.LayoutParams shareparams = (LayoutParams) view.getLayoutParams();
				 int topmargin = shareparams.topMargin;
				 int leftmargin = shareparams.leftMargin;
				 RelativeLayout.LayoutParams shareParams = new RelativeLayout.LayoutParams(40, 40);
				 shareParams.leftMargin = leftmargin +40;
				 shareParams.topMargin = topmargin+40;
				 View fleetview  = layout.findViewWithTag(iiglobal.currentFleet);
				 fleetview.setLayoutParams(shareParams);
				 
				 if (secondFleet!=null && secondFleet.owner.equals(iiglobal.currentFleet.owner))
				 {
					 if(secondFleet.moved)
					 {layout.removeView(layout.findViewWithTag(secondFleet));}
				 }
			 }

			 @Override
			 public void onAnimationRepeat(Animation animation) {
				 // TODO Auto-generated method stub
		
			 }

			 @Override
			 public void onAnimationStart(Animation animation) {
				 iiglobal.DeleteMovementLines(layout);
		
			 }}
				 );
		 
		 layout.findViewWithTag(iiglobal.currentFleet).startAnimation(animation);


		 
		/*
		 //Deleting previous image
		layout.removeView(layout.findViewWithTag(iiglobal.currentFleet));
		
		//+ ADDING new SHIP IMAGE
		RelativeLayout.LayoutParams shareparams = (LayoutParams) view.getLayoutParams();
		int topmargin = shareparams.topMargin;
		int leftmargin = shareparams.leftMargin;
		RelativeLayout.LayoutParams shareParams = new RelativeLayout.LayoutParams(40, 40);
		View fleetview = new View(layout.getContext());

				fleetview.setTag(iiglobal.currentFleet);
				fleetview.setBackgroundResource(R.drawable.circleshape);
				GradientDrawable shapeDrawable = (GradientDrawable)fleetview.getBackground();
				int col = layout.getResources().getColor(iiglobal.colorArray[iiglobal.playerList.indexOf(iiglobal.currentFleet.owner)]);
				shapeDrawable.setColor(col);
				shareParams.leftMargin = leftmargin +40;
				shareParams.topMargin = topmargin+40;
				fleetview.setLayoutParams(shareParams);
				fleetview.setOnClickListener(iiglobal.fleetClickListener);
				layout.addView(fleetview);	
			iiglobal.DeleteMovementLines(layout);
			*/
	}
	
	public void ReleaseBlockadedPlanet()
	{
		if(iiglobal.currentPlanet1.owner!=null && !iiglobal.currentPlanet1.owner.equals(iiglobal.currentFleet.owner))
		{
			boolean hasSomeShipsThere = false;
			for(Fleet fleet:iiglobal.fleetList)
			{
				if(fleet.location.equals(iiglobal.currentPlanet1))
				{
					hasSomeShipsThere=true;
					break;
				}
			}
			if(hasSomeShipsThere==false)
			{
				iiglobal.currentPlanet1.blockaded=false;
				iiglobal.currentPlanet1.owner.victoryPoints++;
				iiglobal.CheckWinner(layout);
			}
		}
	}
	
	public Fleet GetSecondMovedFleet()
	{
		Fleet secondFleet = new Fleet();
		for (Fleet fleet:iiglobal.fleetList)
		{
			if(fleet.location.equals(clickedPlanet))
			{
				if(fleet.moved)
				{
					secondFleet=fleet;
				}
			}
		}
		return secondFleet;
	}
	
	public void JoinFleets(Fleet secondFleet)
	{
		//only if both fleets has moved
		if (iiglobal.currentFleet.moved && secondFleet.moved)
		{
			iiglobal.JoinFleets(iiglobal.currentFleet, secondFleet);
		}
		//or there is no possible movements for second fleet
		else if(!iiglobal.CanFlySomewhere(secondFleet))
		{
			secondFleet.moved=true;
			iiglobal.JoinFleets(iiglobal.currentFleet, secondFleet);
		}
		
	}

}
