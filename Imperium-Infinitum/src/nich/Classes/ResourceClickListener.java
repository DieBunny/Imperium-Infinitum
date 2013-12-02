package nich.Classes;

import nich.Package.ImpInfGlobal;
import nich.Package.R;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ResourceClickListener implements OnClickListener{

	ImpInfGlobal iiglobal;
	Context context;
	RelativeLayout layout;
	
	@Override
	public void onClick(View v) 
	{
		iiglobal = ImpInfGlobal.getInstance();
		context = v.getContext();
		layout = (RelativeLayout) v.getParent();
		ImageButton bbb = (ImageButton) v;
		Resources x =  (Resources) v.getTag();
		
		if(iiglobal.phase==0)
		{
			if(x.linkedPlanet.owner==null)
			{
				CharSequence text = "This is neutral planet"; 
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
			else if(x.linkedPlanet.owner.equals(iiglobal.currentPlayer) && x.linkedPlanet.blockaded==false)
			{
				Resources secondResource = null;
				
				for(Resources res:x.linkedPlanet.planetRes)
				{
					if(!res.equals(x))
					{secondResource = res;}
				}
				if(x.value==1)
				{
				x.value=0;
				iiglobal.currentPlayer.playerRes.add(x);
				this.AddValueUI(x);
				}
				if(secondResource.value==0)
				{
					iiglobal.currentPlayer.playerRes.remove(secondResource);
					secondResource.value=1;
					this.SubtractValueUI(secondResource);
				}
			}
			else
			{
				CharSequence text = "This is not your planet"; 
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
			
		}
		else
		{
			CharSequence text = "This "+x.linkedPlanet.planetType+" planet gives "+x.value+" "+x.type; 
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}

	}
	
	public void AddValueUI(Resources res)
	{
		TextView countView = new TextView(context);
		if (res.type.equals("fuel"))
		{countView = (TextView) layout.findViewById(R.id.fuelCount);}
		if (res.type.equals("research"))
		{countView = (TextView) layout.findViewById(R.id.researchCount);}
		if (res.type.equals("battleship"))
		{countView = (TextView) layout.findViewById(R.id.battleshipCount);}
		if (res.type.equals("destroyer"))
		{countView = (TextView) layout.findViewById(R.id.destroyerCount);}
		if (res.type.equals("frigatte"))
		{countView = (TextView) layout.findViewById(R.id.frigatteCount);}
		
		int count = Integer.parseInt((String) countView.getText());
		String text = Integer.toString(count+1);
		countView.setText(text);
	}
	
	public void SubtractValueUI(Resources res)
	{
		TextView countView = new TextView(context);
		if (res.type.equals("fuel"))
		{countView = (TextView) layout.findViewById(R.id.fuelCount);}
		if (res.type.equals("research"))
		{countView = (TextView) layout.findViewById(R.id.researchCount);}
		if (res.type.equals("battleship"))
		{countView = (TextView) layout.findViewById(R.id.battleshipCount);}
		if (res.type.equals("destroyer"))
		{countView = (TextView) layout.findViewById(R.id.destroyerCount);}
		if (res.type.equals("frigatte"))
		{countView = (TextView) layout.findViewById(R.id.frigatteCount);}
		
		int count = Integer.parseInt((String) countView.getText());
		String text = Integer.toString(count-1);
		countView.setText(text);
	}

}
