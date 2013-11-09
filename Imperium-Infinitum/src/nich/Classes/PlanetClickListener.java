package nich.Classes;

import nich.Package.ImpInfGlobal;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class PlanetClickListener implements OnClickListener{

	ImpInfGlobal iiglobal;
	
	@Override
	public void onClick(View v) {
		iiglobal = ImpInfGlobal.getInstance();
		if (iiglobal.turn==0)
		{
			Planet x = (Planet) v.getTag();
			if(x.homePlanet==true)
			{
				Context context = v.getContext();
				CharSequence text = "";
				if(x.owner!=null)
				{
					text = "This is " + x.owner.name +"`s home planet";
				}
				else
				{
					text = "You clicked " + x.planetType;
					iiglobal.currentPlanet1 = x;
				}
				
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				
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
			
		}

		
	}

}
