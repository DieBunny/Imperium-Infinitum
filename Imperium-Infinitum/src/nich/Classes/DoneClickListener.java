package nich.Classes;

import nich.Package.ImpInfGlobal;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DoneClickListener implements OnClickListener {

	ImpInfGlobal iiglobal;
	@Override
	public void onClick(View v) {
		iiglobal = ImpInfGlobal.getInstance();
		RelativeLayout layout = (RelativeLayout) v.getParent();
		
		//at the end of player turn
		if(iiglobal.phase==0)
		{
			iiglobal.RestoreResourceValues();
			iiglobal.SetZeroUIResources(layout);
			iiglobal.ResolvePlayerFuelResearch();
		}
		if (iiglobal.phase==2)
		{
			iiglobal.FindAndJoinFleets(layout);
		}
		
		if (iiglobal.phase==3)
		{	iiglobal.DiscardFuel();}

		//Gets Next Player/Phase + updates UI
		iiglobal.GetNextPlayer();
		if (iiglobal.phase==2)
		{iiglobal.HandOutShips(layout);}
		
		iiglobal.SetActivePlayerWindow(layout);
		if (iiglobal.currentPlayer.equals(iiglobal.tokenOwner))
		{
			iiglobal.GetNextPhase();
			iiglobal.SetActivePhaseWindow(layout);
			if(iiglobal.phase==0)
			{
				iiglobal.GetNextTokenOwner();
				iiglobal.turn++;
				iiglobal.currentPlayer=iiglobal.tokenOwner;
				iiglobal.SetActivePlayerWindow(layout);
				iiglobal.SetActiveToken(layout);
			}
			if(iiglobal.phase==2)
			{
				iiglobal.HandOutShips(layout);
				iiglobal.FleetsMovedFalse();
			}
			if(iiglobal.phase==0)
			{
				iiglobal.ChangeResourceVisibility(layout);
				iiglobal.RetrieveBlockades();
			}
			if(iiglobal.phase==1)
			{
				iiglobal.ChangeResourceVisibility(layout);
			}
		}
	}

}
