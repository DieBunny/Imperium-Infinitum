package nich.Classes;

import java.util.ArrayList;
import java.util.List;

import nich.Package.R;
import nich.Package.R.color;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;

public class PhasesClickListener implements OnLongClickListener{

	@Override
	public boolean onLongClick(View v) {

		Planet planetPhases = (Planet) v.getTag();
		List<String> phaseList = new ArrayList<String>();
		for (int i=0; i<4;i++)
		{
			if (i==0)
			{phaseList.add("Long range: "+ planetPhases.combatPhases.longRange);}
			if (i==1)
			{phaseList.add("Medium range: "+ planetPhases.combatPhases.mediumRange);}
			if (i==2)
			{phaseList.add("Short range: "+ planetPhases.combatPhases.shortRange);}
			if (i==3)
			{phaseList.add("Last stand: "+ planetPhases.combatPhases.lastStand);}
		}
		
		ListPopupWindow listPopupWindow = new ListPopupWindow(v.getContext());
		listPopupWindow.setAdapter(new ArrayAdapter(v.getContext(), R.drawable.battlephases, phaseList));
		//listPopupWindow.setBackgroundDrawable(R.drawable.battlephases);
		listPopupWindow.setAnchorView(v);
		listPopupWindow.setWidth(180);
        listPopupWindow.setHeight(159);
        listPopupWindow.setModal(true);
        listPopupWindow.show();
		return false;
	}

}
