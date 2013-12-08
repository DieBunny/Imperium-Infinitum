package nich.Classes;

import nich.Package.R;
import nich.Package.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Line extends View {
    
	Paint paint = new Paint();
	public int startx;
	public int starty;
	public int endx;
	public int endy;
	
	public Line(Context context) {
		super(context);
		paint.setColor(getResources().getColor(R.color.Gray));
		paint.setStrokeWidth(4);
	}

    @Override
    public void onDraw(Canvas canvas) {
    	canvas.drawLine(startx, starty, endx, endy, paint);
    }

}
