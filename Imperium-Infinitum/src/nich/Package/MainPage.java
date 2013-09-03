package nich.Package;

import nich.Classes.HexagonDrawing;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainPage extends Activity {
	
	int playerCount = 0;
	ImpInfGlobal iiglobal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		iiglobal = ImpInfGlobal.getInstance();
		
		//gets playerCount from previous activity
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		  playerCount = extras.getInt("PlayerCount");
		}
		
		//generates hexgrid
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainlayout);
		DisplayMetrics dispMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dispMetrics);
		HexagonDrawing hexDraw = new HexagonDrawing();
		hexDraw.DrawHex(layout, this, playerCount, dispMetrics.heightPixels, dispMetrics.widthPixels);
		
		//Displays player names on screen
		for (int i=0; i<playerCount; i++)
		{
			TextView label = new TextView(this);
			label.setTextColor(getResources().getColor(R.color.White));
			label.setTextSize(28);
			RelativeLayout.LayoutParams shareParams = new RelativeLayout.LayoutParams(180, 80);
			shareParams.leftMargin = Math.abs((int) (40));
			shareParams.topMargin = Math.abs((int) (i*40+40));
			label.setLayoutParams(shareParams);
			label.setText(iiglobal.playerList.get(i).name.toString());
			layout.addView(label);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}

}
