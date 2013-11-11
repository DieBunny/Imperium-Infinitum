package nich.Classes;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ResourceClickListener implements OnClickListener{

	@Override
	public void onClick(View v) 
	{
		Resources x =  (Resources) v.getTag();
		Context context = v.getContext();
		CharSequence text = x.type + " " + x.value + " " + x.linkedPlanet.planetType;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

}
