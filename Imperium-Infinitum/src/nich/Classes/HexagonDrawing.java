package nich.Classes;

import nich.Package.R;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class HexagonDrawing {
	
	public void DrawHex (RelativeLayout layout, Context context, int n, int displayHeight, int displayWidth)
	{
		//hex size depends on screen size and player count
		int oneHex = (displayHeight-(displayHeight/5))/(n+2);
		int i, j;
		float y,x;
		//d should be changed. not precise for now
		float d=(float) (oneHex / 2* Math.sqrt(3)-(oneHex/6));// d is the distance between 2 points 
		for(i=0; i<=(n-1); i++) {
		    y = (float) ((Math.sqrt(3)*i*d)/2.0);
		    for (j = 0; j < (2*n-1-i); j++) {
		        x = (float) ((-(2*n-i-2)*d)/2.0 + j*d);
		        RelativeLayout.LayoutParams shareParams = new RelativeLayout.LayoutParams(oneHex, oneHex);
				ImageButton imgBtn = new ImageButton(context);
				imgBtn.setBackgroundColor(Color.TRANSPARENT);
				imgBtn.setImageResource(R.drawable.hexwt);
				shareParams.leftMargin = Math.abs((int) (x+displayWidth/2));
				shareParams.topMargin = Math.abs((int) (y+displayHeight/3));
				imgBtn.setLayoutParams(shareParams);
				imgBtn.setScaleType(ScaleType.FIT_XY);
				//tags - I doubt we will use them
				//just place where to write down array element`s [i][j]
				Planet xxx = new Planet();
				xxx.xPos = i;
				xxx.yPos = j;
				imgBtn.setTag(xxx);
				layout.addView(imgBtn);
		        
		        if (y!=0) {
			        RelativeLayout.LayoutParams shareParams1 = new RelativeLayout.LayoutParams(oneHex, oneHex);
					ImageButton imgBtn1 = new ImageButton(context);
					imgBtn1.setBackgroundColor(Color.TRANSPARENT);
					imgBtn1.setImageResource(R.drawable.hexwt);
					shareParams1.leftMargin = Math.abs((int) (x+displayWidth/2));
					shareParams1.topMargin = Math.abs((int) (-y+displayHeight/3));
					imgBtn1.setLayoutParams(shareParams1);
					imgBtn1.setScaleType(ScaleType.FIT_XY);
					//just place where to write down array element`s [i][j]
					Planet xyx = new Planet();
					xyx.xPos = -i;
					xyx.yPos = j+i;
					imgBtn1.setTag(xyx);
					layout.addView(imgBtn1);
		        }
		    }

		}
	}

}
