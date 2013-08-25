package nich.Classes;

import nich.Package.R;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;

public class HexagonDrawing {
	
	public void DrawHex (RelativeLayout layout, Context context, int n)
	{
		
		int i, j;
		float y,x;
		float d=(float) (80.0 / 2* Math.sqrt(3)-11);// d is the distance between 2 points as indicated in your schema
		for(i=0; i<=(n-1); i++) {
		    y = (float) ((Math.sqrt(3)*i*d)/2.0);
		    for (j = 0; j < (2*n-1-i); j++) {
		        x = (float) ((-(2*n-i-2)*d)/2.0 + j*d);
		        RelativeLayout.LayoutParams shareParams = new RelativeLayout.LayoutParams(80, 80);
				ImageButton imgBtn = new ImageButton(context);
				imgBtn.setBackgroundColor(Color.TRANSPARENT);
				imgBtn.setImageResource(R.drawable.emptyh);
				shareParams.leftMargin = Math.abs((int) (x+220));
				shareParams.topMargin = Math.abs((int) (y+90));
				imgBtn.setLayoutParams(shareParams);
				imgBtn.setScaleType(ScaleType.FIT_XY);
				
				layout.addView(imgBtn);
		        
		        if (y!=0) {
			        RelativeLayout.LayoutParams shareParams1 = new RelativeLayout.LayoutParams(80, 80);
					ImageButton imgBtn1 = new ImageButton(context);
					imgBtn1.setBackgroundColor(Color.TRANSPARENT);
					imgBtn1.setImageResource(R.drawable.emptyh);
					shareParams1.leftMargin = Math.abs((int) (x+220-(d*(i))));
					shareParams1.topMargin = Math.abs((int) (y+90-(d*(i))));
					imgBtn1.setLayoutParams(shareParams1);
					imgBtn1.setScaleType(ScaleType.FIT_XY);
					
					//layout.addView(imgBtn1);
		        	
		        }
		    }

		}
	}

}
