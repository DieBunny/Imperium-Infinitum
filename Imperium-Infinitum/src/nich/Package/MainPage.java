package nich.Package;

import nich.Classes.HexagonDrawing;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainPage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		
		int playerCount = 0;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		  playerCount = extras.getInt("PlayerCount");
		}
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainlayout);
		HexagonDrawing hexDraw = new HexagonDrawing();
		hexDraw.DrawHex(layout, this, playerCount);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}

}
